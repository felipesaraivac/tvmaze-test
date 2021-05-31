package com.test.tvmaze.domain.usecases

import com.test.tvmaze.data.model.Episode
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.data.repository.ShowRepository

class EpisodeUseCases(private val repository: ShowRepository) {

    suspend fun getShow(showId: Int): Show {
        return repository.getShow(showId)
    }

    suspend fun getEpisodes(showId: Int): List<Episode> {
        return repository.getEpisodes(showId)
    }

}