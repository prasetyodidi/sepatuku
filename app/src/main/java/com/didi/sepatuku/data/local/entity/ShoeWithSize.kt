package com.didi.sepatuku.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.didi.sepatuku.domain.model.DetailShoe
import com.didi.sepatuku.domain.model.Shoe

data class ShoeWithSize(
    @Embedded val shoe: ShoeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idShoe"
    )
    val sizes: List<SizeEntity>
){
    fun intoDetailShoe(): DetailShoe{
        return DetailShoe(
            id = shoe.id,
            name = shoe.name,
            price = shoe.price,
            desc = shoe.desc,
            stock = shoe.stock,
            imageUrl = shoe.imgUrl,
            sizes = sizes.map { it.value }
        )
    }

    fun intoShoe(): Shoe{
        return Shoe(
            name = shoe.name,
            price = shoe.price,
            imageUrl = shoe.imgUrl
        )
    }
}