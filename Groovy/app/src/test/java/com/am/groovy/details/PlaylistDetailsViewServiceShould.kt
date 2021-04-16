package com.am.groovy.details

import com.am.groovy.playlist.PlaylistAPI
import com.am.groovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class PlaylistDetailsViewServiceShould: BaseUnitTest() {


    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")

    private val id = "1"

    //the object being testes. all the rest can be mocked
    lateinit var service: PlaylistDetailsService
    // mocked
    private val api: PlaylistAPI = mock()

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
//        whenever(api.fetchPlaylistDetails(id))
//            .thenReturn(
//                playlistDetails
//            )

        service = PlaylistDetailsService(api)
        //force emission from out kotlin flow
        service.fetchPlaylistDetails(id).single()
        // verify that from the mock object api, one time we have called the function fetchPlaylistDetails
        verify(api, times(1)).fetchPlaylistDetails(id)

    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        whenever(api.fetchPlaylistDetails(id))
            .thenReturn(
                playlistDetails
            )

        service = PlaylistDetailsService(api)

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        whenever(api.fetchPlaylistDetails(id))
            .thenThrow(exception)

        service = PlaylistDetailsService(api)

        assertEquals("Something went wrong",
            service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message)
    }

}