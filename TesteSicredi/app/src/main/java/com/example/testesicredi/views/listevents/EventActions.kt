package com.example.testesicredi.views.listevents

import com.example.domain.entity.model.Event

interface EventActions {
    fun onSelectEvent(event: Event)
}