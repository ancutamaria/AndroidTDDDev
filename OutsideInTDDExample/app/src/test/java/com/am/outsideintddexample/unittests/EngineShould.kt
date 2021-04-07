package com.am.outsideintddexample.unittests

import com.am.outsideintddexample.Engine
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class EngineShould {

    private val engine = Engine()

    @Test
    fun turnOn(){
        engine.turnOn()

        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun riseTemperatureWhenItTurnsOn(){
        engine.turnOn()

        assertEquals(95, engine.temperature)
    }
}