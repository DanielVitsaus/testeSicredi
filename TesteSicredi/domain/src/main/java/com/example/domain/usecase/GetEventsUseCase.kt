package com.example.domain.usecase

import com.example.data.repository.EventsRepository
import com.example.domain.entity.map.toEvents
import com.example.domain.entity.model.Event
import java.lang.Exception
import com.example.domain.interactions.Result

class GetEventsUseCase(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(): Result<List<Event>> = try {
        val list = repository.getEvents()
        Result.Success(list.toEvents())
    } catch (e: Exception) {
        Result.Failure(e)
    }
}