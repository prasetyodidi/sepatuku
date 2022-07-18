package com.didi.sepatuku.domain.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {

    suspend fun insertNewItem(item: ShoppingCartEntity)

    fun getAllItems(): Flow<Resource<List<CartItem>>>

    suspend fun updateItem(item: CartItem)

    suspend fun deleteItem(item: CartItem)
}