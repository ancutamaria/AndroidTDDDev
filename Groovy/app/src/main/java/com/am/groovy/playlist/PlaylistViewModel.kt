package com.am.groovy.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach

class PlaylistViewModel(
    repository: PlaylistRepository
): ViewModel() {

    val loader = MutableLiveData<Boolean>()

//    val playlists = MutableLiveData<Result<List<Playlist>>>()
//    init {
//        viewModelScope.launch {
//            repository.getPlaylists().collect {
//                playlists.value =  it
//            }
//        }
//    }

    val playlists = liveData {
        loader.postValue(true)
        emitSource(repository.getPlaylists()
            .onEach {
                loader.postValue(false)
            }
            .asLiveData())
    }

}
