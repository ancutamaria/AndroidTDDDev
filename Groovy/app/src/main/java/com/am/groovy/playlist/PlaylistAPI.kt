package com.am.groovy.playlist

import com.am.groovy.details.PlaylistDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<PlaylistRaw>

    @GET("playlists-details/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetails

}
