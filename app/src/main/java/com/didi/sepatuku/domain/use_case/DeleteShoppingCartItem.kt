package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.domain.repository.ShoppingCartRepository
import timber.log.Timber

class DeleteShoppingCartItem(
    private val repository: ShoppingCartRepository
) {
    suspend operator fun invoke(item: CartItem){
        Timber.d("useCase onDelete : ${item.name}")
        repository.deleteItem(item)
    }
}