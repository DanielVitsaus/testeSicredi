package com.example.testesicredi.views.listevents

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.model.Event
import com.example.domain.interactions.Result
import com.example.domain.usecase.GetEventsUseCase
import com.example.testesicredi.lifecycle.LifecycleEvent
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EventsViewModel(
    private val getEventsUseCase: GetEventsUseCase
): ViewModel(), EventActions {

    private val _onEvents = MutableLiveData<Result<List<Event>>>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _onSelectedEvent = MutableLiveData<LifecycleEvent<Event>>()
    val onSelectedEvent: LiveData<LifecycleEvent<Event>> = _onSelectedEvent

    val events = _onEvents.asFlow()
        .filterIsInstance<Result.Success<List<Event>>>()
        .distinctUntilChanged()
        .map { it.result }
        .asLiveData()

    private val loadError = _onEvents.asFlow()
        .distinctUntilChanged()
        .map { it is Result.Failure }

    val showEvents = combine(loadError, loading.asFlow()) {
            error, load -> !error && !load
    }.asLiveData()

    val showError = combine(loadError, loading.asFlow()) {
            error, load -> error && !load
    }.asLiveData()

    init {
        getEvents()
    }

    override fun onSelectEvent(event: Event) {
        _onSelectedEvent.value = LifecycleEvent(event)
    }

    private fun getEvents() {
        if (_loading.value == true) return
        _loading.value = true
        viewModelScope.launch {
            val result = getEventsUseCase()
            _onEvents.value = result
            _loading.value = false
        }
    }

}