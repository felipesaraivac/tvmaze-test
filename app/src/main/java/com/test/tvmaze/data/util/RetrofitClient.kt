package com.test.tvmaze.data.util

import com.test.tvmaze.data.ShowRepositoryImpl
import com.test.tvmaze.data.repository.ShowRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getapi(): ShowRepository {
        return retrofit.create(ShowRepositoryImpl::class.java)
    }

    companion object {
        //world wide cases
        private const val base_url = "https://api.tvmaze.com" //base url

        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
    }

}