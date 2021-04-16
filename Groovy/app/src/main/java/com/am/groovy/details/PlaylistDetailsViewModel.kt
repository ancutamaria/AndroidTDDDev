package com.am.groovy.details

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel (
    private val service: PlaylistDetailsService
) : ViewModel() {

    val loader = MutableLiveData<Boolean>()
    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            service.fetchPlaylistDetails(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    playlistDetails.postValue(it)
                }
        }
    }
}
