package com.didi.sepatuku.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shoes(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "desc") var price: Int = 0,
    @ColumnInfo(name = "img") var img: Int = 0
)
