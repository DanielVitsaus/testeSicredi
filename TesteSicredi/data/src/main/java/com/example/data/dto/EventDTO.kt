package com.example.data.dto

import java.time.LocalDateTime

data class EventDTO(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val date: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
    val price: Double
)
