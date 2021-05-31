package com.test.tvmaze.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.tvmaze.data.model.Episode
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.domain.usecases.EpisodeUseCases
import kotlinx.coroutines.launch

class ShowViewModel(val useCases: EpisodeUseCases) : ViewModel() {

    val showLiveData = MutableLiveData<ViewState<Show>>()
    val episodeListLiveData = MutableLiveData<ViewState<List<Episode>>>()

    fun getShow(showId: Int) {
        viewModelScope.launch {
            runCatching {
                val show = useCases.getShow(showId)
                showLiveData.value = ViewState.Success(show)
            }.onFailure {
                showLiveData.value = ViewState.Failed(it)
            }
        }
    }

    fun getEpisodes(showId: Int) {
        viewModelScope.launch {
            runCatching {
                val episodes = useCases.getEpisodes(showId)
                episodeListLiveData.value = ViewState.Success(episodes)
            }.onFailure {
                episodeListLiveData.value = ViewState.Failed(it)
            }
        }
    }

}
