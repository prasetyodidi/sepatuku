package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.domain.repository.ShoppingCartRepository

class InsertShoppingCartItem(
    private val repository: ShoppingCartRepository
) {
    suspend operator fun invoke(item: ShoppingCartEntity){
        repository.insertNewItem(item)
    }
}