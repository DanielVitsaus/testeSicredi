package com.example.data.repository

import com.example.data.dto.CheckInDTO
import com.example.data.dto.EventDTO
import com.example.data.repository.network.EventService

class EventsRepositoryImpl(
    private val service: EventService
): EventsRepository {
    override suspend fun getEvents(): List<EventDTO> {
        return service.events()
    }

    override suspend fun getEvent(id: String): EventDTO {
        return service.event(id)
    }

    override suspend fun checkIn(checkInDTO: CheckInDTO) {
        service.checkIn(checkInDTO)
    }
}