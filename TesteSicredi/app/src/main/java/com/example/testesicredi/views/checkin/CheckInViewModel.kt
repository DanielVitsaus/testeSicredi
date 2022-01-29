package com.example.testesicredi.views.checkin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.model.CheckIn
import com.example.domain.usecase.GetCheckInUseCase
import com.example.testesicredi.lifecycle.LifecycleEvent
import kotlinx.coroutines.launch
import com.example.domain.interactions.Result
import com.example.testesicredi.R

class CheckInViewModel(
    private val getCheckInUseCase: GetCheckInUseCase
): ViewModel() {

    private var _eventId: String? = null

    val name = MutableLiveData("")
    val email = MutableLiveData("")

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _nameError = MutableLiveData<Int?>()
    val nameError: LiveData<Int?> = _nameError

    private val _emailError = MutableLiveData<Int?>()
    val emailError: LiveData<Int?> = _emailError

    private val _onRegistered = MutableLiveData<LifecycleEvent<Int>>()
    val onRegistered: LiveData<LifecycleEvent<Int>> = _onRegistered

    fun checkIn(){
        val eventId = _eventId ?: return
        if (_loading.value == true) return

        val name = name.value?.trim() ?: return
        val email = email.value?.trim() ?: return

        val nameErrorCheck = when {
            name.isBlank() -> R.string.name_is_required
            name.length < 3 -> R.string.name_too_short
            else -> null
        }

        val emailErrorCheck = when {
            email.isBlank() -> R.string.email_is_required
            !isValidEmail(email) -> R.string.email_invalid
            else -> null
        }

        _nameError.value = nameErrorCheck
        _emailError.value = emailErrorCheck

        if (nameErrorCheck != null || emailErrorCheck != null) return

        viewModelScope.launch {
            _loading.value = true
            val checkIn = CheckIn(eventId, name, email)
            _onRegistered.value = when(getCheckInUseCase(checkIn)) {
                is Result.Success -> {
                    LifecycleEvent(R.string.registered_to_event_success)
                }
                is Result.Failure -> {
                    LifecycleEvent(R.string.registered_to_event_error)
                }
            }
            _loading.value = false
        }
    }

    fun setEventId(id: String) {
        _eventId = id
    }

    private fun isValidEmail(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}