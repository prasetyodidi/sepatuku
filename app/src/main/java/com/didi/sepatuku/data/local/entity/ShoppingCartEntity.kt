package com.didi.sepatuku.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.didi.sepatuku.domain.model.CartItem

@Entity(
    tableName = "shopping_cart"
)
//indices = [
//Index(
//value = ["name"],
//unique = true
//)
//]
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    var price: Int = 0,
    @ColumnInfo(name = "img_url")
    val imgUrl: String,
    @ColumnInfo
    val amount: Int = 1,
    @ColumnInfo
    val type: String
){
    fun intoCartItem(): CartItem{
        return CartItem(
            id = id,
            name = name,
            price = price,
            amount = amount,
            type = type,
            imageUrl = imgUrl,
        )
    }
}