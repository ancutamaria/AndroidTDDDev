package com.am.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class PlaylistDetailsFeature: BaseUITest() {

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToFirstPlaylistDetails()

        onView(withId(R.id.playlistdetails_name))
                .check(matches(isDisplayed()))

        onView(withId(R.id.playlistdetails_details))
            .check(matches(isDisplayed()))

        assertDisplayed("Hard Rock Cafe")

        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")

    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails(){
        tearDown()

        Thread.sleep(3000)
        navigateToFirstPlaylistDetails()

        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hidesLoader(){
        navigateToFirstPlaylistDetails()

        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 1))
            )
        )
            .perform(click())

        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hidesErrorMessage() {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 1))
            )
        )
            .perform(click())

        Thread.sleep(3000)
        assertNotExist(R.string.generic_error)
    }

    private fun navigateToFirstPlaylistDetails() {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))
            )
        )
            .perform(click())
    }

}