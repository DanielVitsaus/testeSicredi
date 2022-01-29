package com.example.domain.di

import com.example.data.repository.EventsRepository
import com.example.data.repository.EventsRepositoryImpl
import com.example.data.repository.network.EventService
import com.example.domain.usecase.GetCheckInUseCase
import com.example.domain.usecase.GetEventUseCase
import com.example.domain.usecase.GetEventsUseCase
import com.example.domain.utils.GsonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object DomainDependencies {
    val module = module {
        // Use Case
        single { GetEventsUseCase(get()) }
        single { GetEventUseCase(get()) }
        single { GetCheckInUseCase(get()) }

        // Repository
        single<EventsRepository> {
            EventsRepositoryImpl(provideService(get()))
        }

        single { provideRetrofit(get()) }
        single { provideGson() }
    }

    private fun provideService(retrofit: Retrofit) =
        retrofit.create(EventService::class.java)

    private fun provideRetrofit(gson: Gson): Retrofit {
        val baseUrl = if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M) {
            "http://5f5a8f24d44d640016169133.mockapi.io/api/"
        } else { "https://5f5a8f24d44d640016169133.mockapi.io/api/" }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, GsonUtils.LDT_DESERIALIZER)
            .serializeNulls()
            .create()
    }
}