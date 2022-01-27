package com.example.domain.usecase

import com.example.data.repository.EventsRepository
import com.example.domain.entity.map.toEvent
import com.example.domain.entity.model.Event
import com.example.domain.interactions.Result
import java.lang.Exception

class GetEventUseCase(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(id : String): Result<Event> = try {
        val event = repository.getEvent(id)
        Result.Success(event.toEvent())
    } catch (e: Exception) {
        Result.Failure(e)
    }
}