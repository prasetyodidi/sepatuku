package com.didi.sepatuku.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.didi.sepatuku.domain.model.Shoe

@Entity(
    tableName = "shoes",
    indices = [
        Index(
            value = ["name"],
            unique = true
        )
    ]
)
data class ShoeEntity(
    @PrimaryKey()
    var id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "stock") val stock: Int = 0,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "price") val price: Int = 0,
    @ColumnInfo(name = "image_url") val imgUrl: String
) {
    fun intoShoe(): Shoe {
        return Shoe(
            name = name,
            price = price,
            imageUrl = imgUrl
        )
    }
}
