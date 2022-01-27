package com.example.domain.usecase

import com.example.data.repository.EventsRepository
import com.example.domain.entity.map.toDto
import com.example.domain.entity.model.CheckIn
import com.example.domain.interactions.Result
import java.lang.Exception

class GetCheckingUseCase(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(checkIn: CheckIn): Result<Boolean> = try {
        repository.checkIn(checkIn.toDto())
        Result.Success(true)
    } catch (e: Exception) {
        Result.Failure(e)
    }
}