package com.didi.sepatuku

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.didi.sepatuku.presentation.ShoesAdapter
import com.didi.sepatuku.presentation.shopping_cart.ShoppingCartAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class MainActivityTest {
    private val shoesName = "Patrobas equip high black"

    @BeforeEach
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testClickItemNotFound() {
        assertThrows(PerformException::class.java) {
            Espresso.onView(withId(R.id.rv_shoes))
                .perform(
                    RecyclerViewActions.scrollTo<ShoesAdapter.ShoeViewHolder>(
                        hasDescendant(withText("not in the list"))
                    )
                )
        }
    }

    @Test
    fun testClickItemSuccess() {
        Espresso.onView(withId(R.id.rv_shoes))
            .perform(
                RecyclerViewActions.scrollTo<ShoesAdapter.ShoeViewHolder>(
                    hasDescendant(withText(shoesName)),
                )
            )
    }

    @Test
    fun testBottomNavigation() {
        Espresso.onView(withId(R.id.action_home)).perform(click())
        Espresso.onView(withId(R.id.action_like)).perform(click())
        Espresso.onView(withId(R.id.action_cart)).perform(click())
    }

    @Test
    fun testAboutMenu() {
        Espresso.onView(withId(R.id.action_about)).perform(click())
    }

    @Test
    fun testViewDetailShoe() {
        Espresso.onView(withId(R.id.rv_shoes))
            .perform(
                RecyclerViewActions.actionOnItem<ShoesAdapter.ShoeViewHolder>(
                    hasDescendant(withText(shoesName)),
                    click()
                )
            )

        Espresso
            .onView(withText(shoesName))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLikeAndUnlikeShoe() {
        testLikeShoe()
        testUnlikeShoe()
    }

    @Test
    @Disabled
    fun testLikeShoe() {
        Espresso.onView(withId(R.id.rv_shoes))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ShoesAdapter.ShoeViewHolder>(
                    1,
                    click()
                )
            )

        Espresso
            .onView(withId(R.id.tv_shoes_name))
            .check(matches(withText(shoesName)))

        Espresso
            .onView(withId(R.id.btn_favorite))
            .perform(click())
    }

    @Test
    @Disabled
    fun testUnlikeShoe() {
        Espresso.onView(withId(R.id.action_like)).perform(click())

        Espresso.onView(withId(R.id.rv_shoes))
            .perform(
                RecyclerViewActions.actionOnItem<ShoesAdapter.ShoeViewHolder>(
                    hasDescendant(withText(shoesName)),
                    click()
                )
            )

        Espresso
            .onView(withId(R.id.tv_shoes_name))
            .check(matches(withText(shoesName)))

        Espresso
            .onView(withId(R.id.btn_favorite))
            .perform(click())
    }

    @Test
    @Disabled("Development")
    fun testAddCartItem() {
        // click item list shoes
        Espresso.onView(withId(R.id.rv_shoes))
            .perform(
                RecyclerViewActions.actionOnItem<ShoesAdapter.ShoeViewHolder>(
                    hasDescendant(withText(shoesName)),
                    click()
                )
            )

        Espresso
            .onView(withText(shoesName))
            .check(matches(isDisplayed()))

        // scroll
        Espresso
            .onView(withId(R.id.btn_add_item))
            .perform(ViewActions.scrollTo())
            .check(matches(isDisplayed()))

        // click button add cart
        Espresso
            .onView(withId(R.id.btn_add_item))
            .perform(click())

        // check chip
        Espresso
            .onView(withText("36"))
            .check(matches(withText("36")))

        // select size
        Espresso
            .onView(withText("36"))
            .perform(click())

        // click button add to cart
        Espresso
            .onView(withId(R.id.btn_add_to_chart))
            .perform(click())
    }

    @Test
    @Disabled("Done")
    fun testRemoveCartItem(): Unit = runBlocking {
        // click action cart
        Espresso
            .onView(withId(R.id.action_cart))
            .perform(click())

        delay(500)

        // find cart item with specific shoe name and click button trash
        Espresso.onView(withId(R.id.rv_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ShoppingCartAdapter.ListViewHolder>(
                    0,
                    clickChildView(R.id.btn_delete)
                )
            )

        delay(500)

        Espresso.onView(withText("delete"))
            .perform(click())

    }

    @Test
    fun testAddAndRemoveCartItem() = runBlocking{
        testAddCartItem()
        delay(500)
        testRemoveCartItem()
    }

    companion object {
        @JvmStatic
        fun clickChildView(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "click child view"
                }

                override fun perform(uiController: UiController?, view: View?) {
                    view?.let {
                        val v: View = view.findViewById(id)
                        v.performClick()
                    }
                }

            }
        }
    }

}