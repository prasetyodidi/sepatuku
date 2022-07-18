package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.domain.repository.ShoppingCartRepository
import timber.log.Timber

class UpdateShoppingCartItem(
    private val repository: ShoppingCartRepository
) {
    suspend operator fun invoke(item: CartItem){
        Timber.d("use case update item ${item.name} ${item.amount}")
        repository.updateItem(item)
    }
}