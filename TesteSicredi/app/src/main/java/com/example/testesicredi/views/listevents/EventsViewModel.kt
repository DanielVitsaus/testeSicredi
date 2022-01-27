package com.example.testesicredi.views.listevents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.model.Event
import com.example.domain.interactions.Result
import com.example.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.launch

class EventsViewModel(
    private val getEventsUseCase: GetEventsUseCase
): ViewModel() , EventActions{
    private var hasBeenHandled = false
    private val _onEvents = MutableLiveData<Result<List<Event>>>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _onSelectedEvent = MutableLiveData<Event>()
    val onSelectedEvent: LiveData<Event> = _onSelectedEvent

    override fun onSelectEvent(event: Event) {
        _onSelectedEvent.value = getIfNotHandled(event)
    }

    fun getEvents() {
        viewModelScope.launch {
            when(val result = getEventsUseCase()) {
                is Result.Success -> {

                }
                is Result.Failure -> {

                }
            }
        }
    }

    private fun <T> getIfNotHandled(content: T): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}