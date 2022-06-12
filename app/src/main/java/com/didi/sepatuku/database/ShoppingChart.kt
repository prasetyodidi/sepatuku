package com.didi.sepatuku.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(
            value = ["name"],
            unique = true
        )
    ]
)
class ShoppingChart (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var price: Int = 0,
    @ColumnInfo
    var img: Int = 0,
    @ColumnInfo
    var amount: Int = 1,
    @ColumnInfo
    var type: String? = null
)