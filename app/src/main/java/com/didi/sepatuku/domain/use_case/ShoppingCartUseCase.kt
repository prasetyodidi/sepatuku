package com.didi.sepatuku.domain.use_case

data class ShoppingCartUseCase(
    val getShoppingCartItems: GetShoppingCartItems,
    val deleteShoppingCartItem: DeleteShoppingCartItem,
    val updateShoppingCartItem: UpdateShoppingCartItem,
    val insertShoppingCartItem: InsertShoppingCartItem
)