package com.test.tvmaze.data

import com.test.tvmaze.data.model.Episode
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.data.repository.ShowRepository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowRepositoryImpl: ShowRepository {

    @GET("shows/{id}")
    override suspend fun getShow(@Path("id") showId: Int): Show

    @GET("shows")
    override suspend fun getShows(@Query("page") page: Int): List<Show>

    @GET("search/shows")
    override suspend fun search(@Query("q") query: String): List<Show>

    @GET("shows/{id}/episodes")
    override suspend fun getEpisodes(@Path("id") showId: Int): List<Episode>
}