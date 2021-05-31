package com.test.tvmaze.domain.usecases

import com.test.tvmaze.data.model.Show
import com.test.tvmaze.data.repository.ShowRepository

class ShowUseCases(private val repository: ShowRepository) {

    suspend fun getShows(page: Int): List<Show> {
        return repository.getShows(page)
    }

    suspend fun search(query: String): List<Show> {
        return repository.search(query)
    }
}