package com.example.domain.entity.model

import java.time.LocalDateTime

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val date: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
    val price: Double
)
