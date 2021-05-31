package com.test.tvmaze.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.domain.usecases.ShowUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowsListViewModel(val useCases: ShowUseCases) : ViewModel() {

    val showListLiveData = MutableLiveData<ViewState<List<Show>>>()

    fun loadShows(page: Int = 0) {
        viewModelScope.launch {
            runCatching {
                val showList = useCases.getShows(page)
                showListLiveData.value = ViewState.Success(showList)
            }.onFailure {
                showListLiveData.value = ViewState.Failed(it)
            }
        }
    }

    private var searchJob: Job = Job()
    fun search(query: String) {
        searchJob.cancel()
        searchJob = viewModelScope.launch {
            runCatching {
                val showList = useCases.search(query)
                showListLiveData.value = ViewState.Success(showList)
            }.onFailure {
                showListLiveData.value = ViewState.Failed(it)
            }
        }
    }
}