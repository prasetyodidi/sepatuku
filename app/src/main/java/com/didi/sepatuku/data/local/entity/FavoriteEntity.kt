package com.didi.sepatuku.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.didi.sepatuku.domain.model.Shoe

@Entity(tableName = "favorites")
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int = 0,
    @ColumnInfo(name = "img_url") val imgUrl: String
) {
    fun intoShoe(): Shoe{
        return Shoe(
            name = name,
            price = price,
            imageUrl = imgUrl
        )
    }
}