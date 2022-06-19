package com.didi.sepatuku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoe(
    val id: String,
    val desc: String,
    val name: String,
    val price: Int,
    val stock: Int,
    val sizes: List<Int>,
    val image_url: String
) : Parcelable