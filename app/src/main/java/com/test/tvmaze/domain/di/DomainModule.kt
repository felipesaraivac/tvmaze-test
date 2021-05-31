package com.test.tvmaze.domain.di

import androidx.work.WorkManager
import com.test.tvmaze.domain.usecases.EpisodeUseCases
import com.test.tvmaze.domain.usecases.ShowUseCases
import com.test.tvmaze.ui.di.initKtorClient
import org.koin.dsl.module

private val domainModule = module {
    single { WorkManager.getInstance(get()) }
    single { initKtorClient() }

    factory {
        ShowUseCases(
            repository = get()
        )
    }

    factory {
        EpisodeUseCases(
            repository = get()
        )
    }
}

val domainModules = listOf(domainModule)