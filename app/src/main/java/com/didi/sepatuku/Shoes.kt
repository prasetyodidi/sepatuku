package com.didi.sepatuku

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoes(
        var name: String? = "",
        var price: Int = 0,
        var description: String? = "",
        var sizes: List<Int> = listOf(36, 37),
        var photo: Int = 0,
        var stock: Int = 0
) : Parcelable
