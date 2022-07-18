package com.didi.sepatuku.domain.model

import com.didi.sepatuku.data.local.entity.FavoriteEntity

data class Shoe(
    val name: String,
    val price: Int,
    val imageUrl: String
){
    fun intoFavoriteEntity(): FavoriteEntity{
        return FavoriteEntity(
            name = name,
            price = price,
            imgUrl = imageUrl
        )
    }
}