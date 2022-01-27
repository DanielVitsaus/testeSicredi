package com.example.data.repository

import com.example.data.dto.CheckInDTO
import com.example.data.dto.EventDTO

interface EventsRepository {
    suspend fun getEvents(): List<EventDTO>
    suspend fun getEvent(id: String): EventDTO
    suspend fun checkIn(checkInDTO: CheckInDTO)
}