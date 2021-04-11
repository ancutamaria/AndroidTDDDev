package com.am.groovy.playlist

import com.am.groovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class PlaylistServiceShould: BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<Playlist> = mock()

    @Test
    fun fetchPlaylistsFromAPI() = runBlockingTest{

        service = PlaylistService(api)

        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest{
        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }


    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockFailureCase()
        assertEquals("Something went wrong", service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists())
                .thenReturn(playlists)

        service = PlaylistService(api)
    }

    private fun mockFailureCase() {
        whenever(api.fetchAllPlaylists())
                .thenThrow(RuntimeException("Ups"))
        service = PlaylistService(api)
    }

}