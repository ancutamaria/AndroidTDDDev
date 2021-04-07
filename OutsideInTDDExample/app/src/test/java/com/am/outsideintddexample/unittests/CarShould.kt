package com.am.outsideintddexample.unittests

import com.am.outsideintddexample.Car
import com.am.outsideintddexample.Engine
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

/*
    the naming convention approach is inspired from Sandro Mancuso
    in unit tests we only care about the tested object, all the
    collaborators/dependencies are mocked
*/

class CarShould {

    private val engine: Engine = mock()
    private val car = Car( engine,5.0)

    @Test
    fun loseFuelWhenItTurnsOn(){
        car.turnOn()

        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnOnItsEngine(){
        car.turnOn()

        verify(engine, times(1)).turnOn()
    }
}