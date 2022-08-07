package com.didi.sepatuku

import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Assert.*
import org.junit.jupiter.api.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.didi.sepatuku", appContext.packageName)
    }
}