package com.didi.sepatuku.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.didi.sepatuku.core.util.Helper.Companion.loadImage
import com.didi.sepatuku.databinding.ItemShoeBinding
import com.didi.sepatuku.domain.model.Shoe

class ShoesAdapter : RecyclerView.Adapter<ShoesAdapter.ShoeViewHolder>() {
    private var listItems: MutableList<Shoe> = mutableListOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<Shoe>) {
        val diffCallback = ShoeDiffCallback(this.listItems, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listItems.clear()
        this.listItems.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeViewHolder {
        val binding = ItemShoeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoeViewHolder(binding)
    }

    inner class ShoeViewHolder(private val binding: ItemShoeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shoe) {
            binding.apply {
                tvItemName.text = item.name
                tvItemPrice.text = item.price.toString()
                imgItemPhoto.loadImage(item.imageUrl)
            }
            this.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }
    }

    override fun onBindViewHolder(holder: ShoeViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int = listItems.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Shoe)
    }
}

private class ShoeDiffCallback(
    private val oldListShoes: List<Shoe>,
    private val newListShoes: List<Shoe>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListShoes.size
    }

    override fun getNewListSize(): Int {
        return newListShoes.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListShoes[oldItemPosition].name == newListShoes[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListShoes[oldItemPosition].price == newListShoes[newItemPosition].price &&
                oldListShoes[oldItemPosition].imageUrl == newListShoes[newItemPosition].imageUrl
    }

}