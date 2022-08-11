package com.didi.sepatuku.presentation.detail_shoe

import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.data.repository.FakeShoeFavoriteRepository
import com.didi.sepatuku.data.repository.FakeShoeRepository
import com.didi.sepatuku.data.repository.FakeShoppingCartRepository
import com.didi.sepatuku.domain.use_case.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class DetailShoeViewModelTest {
    private lateinit var viewModel: DetailShoeViewModel

    @BeforeEach
    fun setUp() {
        val fakeShoeRepository = FakeShoeRepository()
        val fakeFavoriteRepository = FakeShoeFavoriteRepository()
        val fakeShoppingCartRepository = FakeShoppingCartRepository()
        val fakeShoeUseCase = ShoeUseCase(
            GetShoes(fakeShoeRepository),
            GetDetailShoe(fakeShoeRepository)
        )
        val fakeFavoriteUseCase = FavoriteUseCase(
            GetFavorites(fakeFavoriteRepository),
            DeleteFavorite(fakeFavoriteRepository),
            InsertFavorite(fakeFavoriteRepository)
        )
        val fakeShoppingCartUseCase = ShoppingCartUseCase(
            GetShoppingCartItems(fakeShoppingCartRepository),
            DeleteShoppingCartItem(fakeShoppingCartRepository),
            UpdateShoppingCartItem(fakeShoppingCartRepository),
            InsertShoppingCartItem(fakeShoppingCartRepository)
        )

        viewModel = DetailShoeViewModel(
            fakeShoeUseCase,
            fakeFavoriteUseCase,
            fakeShoppingCartUseCase
        )
    }

    @Test
    fun `test get detail shoe`(): Unit = runBlocking {
        viewModel.getDetailShoe("a")
        delay(1000)

        // verify
        viewModel.state.first().let { state ->
            assertEquals("a", state.detailShoe?.name)
            assertEquals(false, state.isFavorite)
            println(state.detailShoe)
            println(state.isFavorite)
            println(state.message)
            println(state.isLoading)
        }

    }

    @Test
    fun `test get not found detail`() = runBlocking{
        viewModel.getDetailShoe("az")
        delay(1000)

        // verify
        viewModel.state.first().let { state ->
            assertNull(state.detailShoe)
            assertEquals(false, state.isFavorite)
            println(state.detailShoe)
            println(state.isFavorite)
            println(state.message)
            println(state.isLoading)
        }
    }

    @Test
    fun `test change favorite`() = runBlocking {
        viewModel.getDetailShoe("a")
        delay(1000)

        // verify
        viewModel.state.first().let { state ->
            assertEquals("a", state.detailShoe?.name)
            assertEquals(false, state.isFavorite)
            println(state.detailShoe)
            println(state.isFavorite)
            println(state.message)
            println(state.isLoading)
        }

        // change favorite -> true
        viewModel.changeFavorite()

        // verify
        viewModel.state.first().let { state ->
            assertEquals(true, state.isFavorite)
            println(state.detailShoe)
            println(state.isFavorite)
            println(state.message)
            println(state.isLoading)
        }

        // change favorite -> false
        viewModel.changeFavorite()

        // verify
        viewModel.state.first().let { state ->
            println(state.detailShoe)
            println(state.isFavorite)
            println(state.message)
            println(state.isLoading)
        }
    }

    @Test
    fun `test add item to cart`() = runBlocking {
        val item = ShoppingCartEntity(
            id = 1,
            name = "az",
            price = 1,
            imgUrl = "az",
            amount = 1,
            type = "az"
        )

        assertDoesNotThrow { viewModel.insertShoppingCart(item) }

    }

}