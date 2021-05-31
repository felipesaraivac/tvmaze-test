package com.test.tvmaze.data.repository

import com.test.tvmaze.data.model.Episode
import com.test.tvmaze.data.model.Show

interface ShowRepository {

    suspend fun getShow(showId: Int): Show

    suspend fun getShows(page: Int): List<Show>

    suspend fun search(query: String): List<Show>

    suspend fun getEpisodes(showId: Int): List<Episode>

}