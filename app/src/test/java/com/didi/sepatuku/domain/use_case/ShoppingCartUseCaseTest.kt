package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.data.repository.FakeShoppingCartRepository
import com.didi.sepatuku.domain.model.CartItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class ShoppingCartUseCaseTest{

    @Test
    fun `test delete item`() = runBlocking{
        val item = CartItem(
            id = 3,
            name = "c",
            price = 3,
            imageUrl = "c",
            type = "3",
            amount = 3
        )
        assertDoesNotThrow {
            runBlocking {
                shoppingCartUseCase.deleteShoppingCartItem(item)
            }
        }
    }

    @Test
    fun `test update item`(){
        val item = CartItem(
            id = 4,
            name = "d",
            price = 4,
            imageUrl = "d",
            type = "4",
            amount = 4
        )
        assertDoesNotThrow {
            runBlocking {
                shoppingCartUseCase.updateShoppingCartItem(item)
            }
        }
    }

    @Test
    fun `test get all items`(){
        assertDoesNotThrow {
            runBlocking {
                val items = shoppingCartUseCase.getShoppingCartItems().first()
                assertEquals(26, items.data?.size)
            }
        }
    }

    companion object{
        lateinit var shoppingCartUseCase: ShoppingCartUseCase
        private lateinit var fakeRepository: FakeShoppingCartRepository

        @BeforeAll
        @JvmStatic
        fun setUp(){
            fakeRepository = FakeShoppingCartRepository()
            shoppingCartUseCase = ShoppingCartUseCase(
                GetShoppingCartItems(fakeRepository),
                DeleteShoppingCartItem(fakeRepository),
                UpdateShoppingCartItem(fakeRepository),
                InsertShoppingCartItem(fakeRepository)
            )
            val listInsert = mutableListOf<CartItem>()
            ('a'..'z').forEachIndexed { index, c ->
                val item = CartItem(
                    id = index,
                    name = c.toString(),
                    price = index,
                    imageUrl = c.toString(),
                    type = c.toString(),
                    amount = index
                )
                listInsert.add(item)
            }
            listInsert.shuffle()
            listInsert.forEach { item ->
                runBlocking {
                    shoppingCartUseCase.insertShoppingCartItem(item.intoShoppingCartEntity())
                }
            }
        }
    }
}

