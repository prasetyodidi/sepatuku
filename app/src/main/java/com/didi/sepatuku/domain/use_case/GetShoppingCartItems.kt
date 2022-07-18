package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingCartItems(
    private val repository: ShoppingCartRepository
) {
    operator fun invoke(): Flow<Resource<List<CartItem>>>{
        return repository.getAllItems()
    }

}