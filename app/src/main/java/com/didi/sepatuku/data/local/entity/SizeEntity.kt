package com.didi.sepatuku.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sizes")
data class SizeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "idShoe") var idShoe: String,
    @ColumnInfo(name = "value") var value: Int = 0
)
