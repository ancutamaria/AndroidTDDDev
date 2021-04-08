package com.am.outsideintddexample.unittests

import com.am.outsideintddexample.Car
import com.am.outsideintddexample.Engine
import com.example.outsideintddexample.acceptancetests.MainCoroutineScopeRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

/*
    the naming convention approach is inspired from Sandro Mancuso
    in unit tests we only care about the tested object, all the
    collaborators/dependencies are mocked
*/

class CarShould {

    private val engine: Engine = mock()
    private val car = Car(engine,5.0)

    @get:Rule
    var coroutineScopeRule = MainCoroutineScopeRule()

    init {
        runBlocking {
            whenever(engine.turnOn()).thenReturn(flow {
                emit(25)
                delay(1000)
                emit(50)
                delay(1000)
                emit(95)
            })
        }
    }

    @Test
    fun loseFuelWhenItTurnsOn() = runBlockingTest{
        car.turnOn()

        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnOnItsEngine() = runBlockingTest{
        car.turnOn()

        verify(engine, times(1)).turnOn()
    }
}