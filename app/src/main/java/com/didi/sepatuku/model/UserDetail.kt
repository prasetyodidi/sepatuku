package com.didi.sepatuku.model

data class UserDetail(
    val desc: String,
    val id: String,
    val image_url: String,
    val name: String,
    val price: Int,
    val sizes: List<Int>,
    val stock: Int
)