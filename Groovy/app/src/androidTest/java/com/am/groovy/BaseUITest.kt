package com.am.groovy

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.am.groovy.playlist.idlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {


    val activityTestRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object: TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(item: View): Boolean {
                if (item.parent !is ViewGroup) return false
                val parent = item.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == item)
            }
        }
    }

}