package com.example.testesicredi

import androidx.multidex.MultiDexApplication
import com.example.domain.di.DomainDependencies
import com.example.testesicredi.di.EventsDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EventsApp: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        val modules = mutableListOf(
            DomainDependencies.module,
            EventsDependencies.module
        )
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            loadKoinModules(modules)
        }
    }
}