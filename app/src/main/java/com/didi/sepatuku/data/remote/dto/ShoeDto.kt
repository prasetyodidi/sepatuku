package com.didi.sepatuku.data.remote.dto

import com.didi.sepatuku.data.local.entity.ShoeEntity
import com.didi.sepatuku.data.local.entity.ShoeWithSize
import com.didi.sepatuku.data.local.entity.SizeEntity

data class ShoeDto(
    val desc: String,
    val id: String,
    val image_url: String,
    val name: String,
    val price: Int,
    val sizes: List<Int>,
    val stock: Int
){
    fun intoShoeWithSize(): ShoeWithSize {
        return ShoeWithSize(
            shoe = ShoeEntity(
                id = id,
                name = name,
                stock = stock,
                desc = desc,
                price = price,
                imgUrl = image_url
            ),
            sizes = sizes.map { SizeEntity(idShoe = id, value = it) }
        )
    }
}