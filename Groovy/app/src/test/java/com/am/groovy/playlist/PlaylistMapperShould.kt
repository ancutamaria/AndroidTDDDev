package com.am.groovy.playlist

import com.am.groovy.R
import com.am.groovy.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistMapperShould: BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "name", "category")
    private val playlistRockRaw = PlaylistRaw("1", "name", "rock")

    private val mapper = PlaylistMapper()

    private val playlist = mapper.invoke(listOf(playlistRaw))[0]
    private val playlistRock = mapper(listOf(playlistRockRaw))[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName(){
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory(){
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock(){
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRock(){
        assertEquals(R.mipmap.rock, playlistRock.image)
    }
}