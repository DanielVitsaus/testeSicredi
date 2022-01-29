package com.example.testesicredi.lifecycle

import androidx.lifecycle.Observer

open class LifecycleEvent<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peek(): T = content

    fun markHandled() {
        hasBeenHandled = true
    }
}

class LifecycleEventObserver<T>(private val onEventUnhandled: (T) -> Unit) : Observer<LifecycleEvent<T>> {
    override fun onChanged(event: LifecycleEvent<T>?) {
        event?.getIfNotHandled()?.let { value ->
            onEventUnhandled(value)
        }
    }
}