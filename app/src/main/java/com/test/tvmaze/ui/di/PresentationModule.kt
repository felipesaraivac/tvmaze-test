package com.test.tvmaze.ui.di

import androidx.work.WorkManager
import com.test.tvmaze.ui.viewmodel.ShowViewModel
import com.test.tvmaze.ui.viewmodel.ShowsListViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.logging.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module(override = true) {
    single { WorkManager.getInstance(get()) }
    single { initKtorClient() }

    viewModel {
        ShowsListViewModel(
            useCases = get()
        )
    }

    viewModel {
        ShowViewModel(
            useCases = get()
        )
    }
}

fun initKtorClient() = HttpClient(Android) {
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
    }
}
