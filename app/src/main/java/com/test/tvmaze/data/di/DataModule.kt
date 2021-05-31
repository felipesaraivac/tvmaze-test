package com.test.tvmaze.data.di

import com.test.tvmaze.data.repository.ShowRepository
import com.test.tvmaze.data.util.RetrofitClient
import org.koin.dsl.module

private val repositoryModule = module(override = false) {
    factory<ShowRepository> {
        RetrofitClient.instance!!.getapi()
    }
}

val dataModules = listOf(repositoryModule)