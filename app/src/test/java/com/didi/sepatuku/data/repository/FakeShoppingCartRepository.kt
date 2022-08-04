package com.didi.sepatuku.data.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeShoppingCartRepository: ShoppingCartRepository {
    private val cartItems = mutableListOf<CartItem>()

    override suspend fun insertNewItem(item: ShoppingCartEntity) {
        cartItems.add(item.intoCartItem())
    }

    override fun getAllItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(
            Resource.Success(data = cartItems)
        )
    }

    override suspend fun updateItem(item: CartItem) {
        val byId = cartItems.find { it.id == item.id }
        if (byId != null){
            cartItems.remove(byId)
            cartItems.add(item)
        }
    }

    override suspend fun deleteItem(item: CartItem) {
        cartItems.remove(item)
    }
}