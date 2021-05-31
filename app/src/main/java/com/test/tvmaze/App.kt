package com.test.tvmaze

import android.app.Application
import com.test.tvmaze.data.di.dataModules
import com.test.tvmaze.domain.di.domainModules
import com.test.tvmaze.ui.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModules + domainModules + listOf(presentationModule))
        }
    }
}