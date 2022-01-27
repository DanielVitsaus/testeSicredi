package com.example.data.repository.network

import com.example.data.dto.CheckInDTO
import com.example.data.dto.EventDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {
    @GET("events")
    suspend fun events(): List<EventDTO>

    @GET("events/{id}")
    suspend fun event(@Path("id") id: String): EventDTO

    @POST("checkin")
    suspend fun checkIn(@Body data: CheckInDTO): Any
}
