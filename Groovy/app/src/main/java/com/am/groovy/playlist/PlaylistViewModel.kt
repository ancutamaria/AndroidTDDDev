package com.am.groovy.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData

class PlaylistViewModel(
    repository: PlaylistRepository
): ViewModel() {

//    val playlists = MutableLiveData<Result<List<Playlist>>>()
//    init {
//        viewModelScope.launch {
//            repository.getPlaylists().collect {
//                playlists.value =  it
//            }
//        }
//    }

    val playlists = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }

}
