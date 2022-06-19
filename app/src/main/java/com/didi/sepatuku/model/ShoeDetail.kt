package com.didi.sepatuku.model

data class ShoeDetail(
    val id: String,
    val desc: String,
    val name: String,
    val price: Int,
    val stock: Int,
    val sizes: List<Int>,
    val image_url: String
)