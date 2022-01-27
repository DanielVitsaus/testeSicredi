package com.example.testesicredi.di

import com.example.data.repository.EventsRepository
import com.example.data.repository.EventsRepositoryImpl
import com.example.data.repository.network.EventService
import com.example.domain.usecase.GetEventsUseCase
import com.example.domain.utils.GsonUtils
import com.example.testesicredi.views.listevents.EventsViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object EventsDependencies {
    val module = module {
        // View models
        viewModel { EventsViewModel(get()) }

        // Use Case
        single { GetEventsUseCase(get()) }

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
       return Retrofit.Builder()
            .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
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