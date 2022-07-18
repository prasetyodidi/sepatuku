package com.didi.sepatuku.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didi.sepatuku.databinding.ItemShoeBinding
import com.didi.sepatuku.domain.model.Shoe
import timber.log.Timber

class ShoesAdapter : RecyclerView.Adapter<ShoesAdapter.ShoeViewHolder>() {
    private var listItems: MutableList<Shoe> = mutableListOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<Shoe>) {
        if (items.isNotEmpty()) {
            Timber.d("set data ${items.size} ${items[0].name}")
        }
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
                Glide.with(this@ShoeViewHolder.itemView.context)
                    .load(item.imageUrl)
                    .into(imgItemPhoto)
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