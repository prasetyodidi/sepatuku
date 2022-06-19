package com.didi.sepatuku.helper

import androidx.recyclerview.widget.DiffUtil
import com.didi.sepatuku.model.Shoe

class ShoeDiffCallback(
    private val oldShoe: List<Shoe>,
    private val newShoe: List<Shoe>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldShoe.size
    }

    override fun getNewListSize(): Int {
        return newShoe.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldShoe[oldItemPosition].id == newShoe[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldShoe[oldItemPosition].name == newShoe[newItemPosition].name &&
        oldShoe[oldItemPosition].price == newShoe[newItemPosition].price &&
        oldShoe[oldItemPosition].desc == newShoe[newItemPosition].desc &&
        oldShoe[oldItemPosition].stock == newShoe[newItemPosition].stock &&
        oldShoe[oldItemPosition].sizes == newShoe[newItemPosition].sizes &&
        oldShoe[oldItemPosition].image_url == newShoe[newItemPosition].image_url
    }
}