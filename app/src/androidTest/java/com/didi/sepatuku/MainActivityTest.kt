package com.didi.sepatuku

import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.contrib.RecyclerViewActions
import com.didi.sepatuku.presentation.ShoesAdapter
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MainActivityTest{

    @Rule
    val activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testClickItemNotFound() {
        assertThrows(PerformException::class.java){
            Espresso.onView(ViewMatchers.withId(R.id.rv_shoes))
                .perform(RecyclerViewActions.scrollTo<ShoesAdapter.ShoeViewHolder>(
                    hasDescendant(withText("not in the list"))
                ))
        }
    }

    @Test
    fun testClickItemSuccess() {
        assertThrows(PerformException::class.java){
            Espresso.onView(ViewMatchers.withId(R.id.rv_shoes))
                .perform(RecyclerViewActions.scrollTo<ShoesAdapter.ShoeViewHolder>(
                    hasDescendant(withText("patrobas equip high black"))
                ))
        }
    }
}