package com.didi.sepatuku.domain.model

import com.didi.sepatuku.data.local.entity.ShoppingCartEntity

data class CartItem(
    val id: Int = -1,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val type: String,
    val amount: Int
){
    fun intoShoppingCartEntity(): ShoppingCartEntity{
        return ShoppingCartEntity(
            id = id,
            name = name,
            price = price,
            type = type,
            amount = amount,
            imgUrl = imageUrl
        )
    }
}
