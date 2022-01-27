package com.example.domain.entity.map

import com.example.data.dto.CheckInDTO
import com.example.data.dto.EventDTO
import com.example.domain.entity.model.CheckIn
import com.example.domain.entity.model.Event

fun EventDTO.toEvent() = Event(
    id,
    title,
    description,
    image,
    date,
    latitude,
    longitude,
    price
)

fun List<EventDTO>?.toEvents() = this?.map { it.toEvent() } ?: listOf()

fun CheckIn.toDto() = CheckInDTO(eventId, name, email)