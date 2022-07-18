package com.didi.sepatuku.domain.model

data class DetailShoe(
    val desc: String,
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val sizes: List<Int>,
    val stock: Int,
    var isFavorite: Boolean = false
){

    fun intoShoe(): Shoe{
        return Shoe(
            name = name,
            price = price,
            imageUrl = imageUrl
        )
    }
}