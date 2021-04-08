package com.am.outsideintddexample.accentancetests

import com.am.outsideintddexample.Car
import com.am.outsideintddexample.Engine
import com.example.outsideintddexample.acceptancetests.MainCoroutineScopeRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

/*
    in acceptance tests we do not mock anything, for everything it interacts
    with collaborators/dependencies we create real objects
    here we test the the whole system is working
*/

class CarFeature {

    private val engine = Engine()
    private val car = Car(engine, 6.0)

    @get:Rule
    var coroutineScopeRule = MainCoroutineScopeRule()

    @Test
    fun carIsLosingFuelWhenItTurnsOn(){

        car.turnOn()

        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnItsEngineAndIncreasesTheTemperature(){
        car.turnOn()

        assertEquals(15, car.engine.temperature)

        coroutineScopeRule.advanceTimeBy(1000)
        assertEquals(25, car.engine.temperature)
        coroutineScopeRule.advanceTimeBy(1000)
        assertEquals(50, car.engine.temperature)
        coroutineScopeRule.advanceTimeBy(1000)
        assertEquals(95, car.engine.temperature)
        assertTrue(car.engine.isTurnedOn)
    }
}