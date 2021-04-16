package com.am.groovy.details

import com.am.groovy.utils.BaseUnitTest
import com.am.groovy.utils.captureValues
import com.am.groovy.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class PlaylistDetailsViewModelShould: BaseUnitTest() {

    private val service: PlaylistDetailsService = mock()
    private val playlistDetails = mock<PlaylistDetails>()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")

    private val id = "1"

    lateinit var viewModel: PlaylistDetailsViewModel

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest{

        whenever(service.fetchPlaylistDetails(id))
            .thenReturn(
                flow {
                    emit(expected)
                }
            )

        val viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() = runBlockingTest {

        whenever(service.fetchPlaylistDetails(id))
            .thenReturn(
                flow {
                    emit(expected)
                }
            )

        val viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())

    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {
        whenever(service.fetchPlaylistDetails(id))
            .thenReturn(
                flow {
                    emit(Result.failure<PlaylistDetails>(exception))
                }
            )

        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
        assertEquals(exception, viewModel.playlistDetails.getValueForTest()!!.exceptionOrNull())

    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel = PlaylistDetailsViewModel(service)


        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            Assert.assertEquals(true, values.first())
        }
    }

    @Test
    fun closeLoaderAfterPlaylistsLoad() = runBlockingTest {
        whenever(service.fetchPlaylistDetails(id))
            .thenReturn(
                flow {
                    emit(expected)
                }
            )

        val viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)

        viewModel.loader.captureValues {
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

//    @Test
//    fun closeLoaderAfterError() = runBlockingTest {
//        whenever(service.fetchPlaylistDetails(id))
//            .thenReturn(
//                flow {
//                    emit(Result.failure<PlaylistDetails>(exception))
//                }
//            )
//
//        viewModel = PlaylistDetailsViewModel(service)
//
//        viewModel.loader.captureValues {
//            viewModel.playlistDetails.getValueForTest()
//
//            assertEquals(false, values.last())
//        }
//    }

}