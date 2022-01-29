package com.example.testesicredi.di

import com.example.testesicredi.views.checkin.CheckInViewModel
import com.example.testesicredi.views.details.EventDetailsViewModel
import com.example.testesicredi.views.listevents.EventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object EventsDependencies {
    val module = module {
        // View models
        viewModel { EventsViewModel(get()) }
        viewModel { EventDetailsViewModel(get()) }
        viewModel { CheckInViewModel(get()) }
    }
}