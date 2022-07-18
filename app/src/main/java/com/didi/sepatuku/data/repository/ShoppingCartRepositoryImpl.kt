package com.didi.sepatuku.data.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.data.local.dao.ShoppingCartDao
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.sql.SQLException

class ShoppingCartRepositoryImpl(
    private val dao: ShoppingCartDao
): ShoppingCartRepository {
    override suspend fun insertNewItem(item: ShoppingCartEntity) {
        val result = dao.getAll().first()
        val resultItem: ShoppingCartEntity? = result.find { it.name == item.name && it.type == item.type }
        if (resultItem != null){
            dao.update(resultItem.copy(amount = resultItem.amount + 1))
        } else {
            dao.insert(item)
        }
    }

    override fun getAllItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())

        val result = dao.getAll()

        result
            .distinctUntilChanged()
            .collect { items ->
            emit(Resource.Success(data = items.map { it.intoCartItem() }))
        }
    }

    override suspend fun updateItem(item: CartItem) {
        dao.update(item.intoShoppingCartEntity())
    }

    override suspend fun deleteItem(item: CartItem) {
        dao.delete(item.intoShoppingCartEntity())
    }
}