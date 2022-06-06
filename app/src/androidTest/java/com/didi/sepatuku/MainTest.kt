package com.didi.sepatuku

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainTest {

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun clickItem(){
        onView(withId(R.id.img_person)).perform(click())
    }
}