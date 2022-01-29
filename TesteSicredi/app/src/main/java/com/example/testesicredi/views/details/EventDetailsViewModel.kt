package com.example.testesicredi.views.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.model.Event
import com.example.domain.interactions.Result
import com.example.domain.usecase.GetEventUseCase
import com.example.testesicredi.lifecycle.LifecycleEvent
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class EventDetailsViewModel(
    private val getEventUseCase: GetEventUseCase
): ViewModel() {

    private val _onEvent = MutableLiveData<Result<Event>>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _onCheckInEvent = MutableLiveData<LifecycleEvent<Event>>()
    val onCheckInEvent: LiveData<LifecycleEvent<Event>> = _onCheckInEvent

    val onEvent = _onEvent.asFlow()
        .filterIsInstance<Result.Success<Event>>()
        .distinctUntilChanged()
        .map { it.result }
        .asLiveData()

    private val loadError = _onEvent.asFlow()
        .distinctUntilChanged()
        .map { it is Result.Failure }

    val showEvent = combine(loadError, loading.asFlow()) {
            error, load -> !error && !load
    }.asLiveData()

    val showError = combine(loadError, loading.asFlow()) {
            error, load -> error && !load
    }.asLiveData()

    fun getEvent(idEvent: String) {
        if (_loading.value == true) return
        _loading.value = true
        viewModelScope.launch {
            val result = getEventUseCase(idEvent)
            _onEvent.value = result
            _loading.value = false
        }
    }

    fun getInfoEvent(): String {
        val event = onEvent.value ?: return ""
        val pattern = "EEEE, dd MMMM yyyy 'as' HH:mm"
        return "O evento '${event.title}'\n" +
            "Sera realizado na ${event.date.format(DateTimeFormatter.ofPattern(pattern))}" +
            "\ncom o custo de ${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(event.price)}"
    }

    fun checkInEvent(event: Event) {
        _onCheckInEvent.value = LifecycleEvent(event)
    }
}