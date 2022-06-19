package com.didi.sepatuku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didi.sepatuku.databinding.ItemShoeBinding
import com.didi.sepatuku.model.Shoe

class ShoesAdapter : ListAdapter<Shoe, RecyclerView.ViewHolder>(ShoeDiffCallback()) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShoeViewHolder(
            ItemShoeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shoe = getItem(position)
        (holder as ShoeViewHolder).bind(shoe)
    }

    inner class ShoeViewHolder(private val binding: ItemShoeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shoe) {

            binding.apply {
                tvItemName.text = item.name
                tvItemPrice.text = item.price.toString()
                Glide.with(this@ShoeViewHolder.itemView.context)
                    .load(item.image_url)
                    .into(imgItemPhoto)
            }
            this.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Shoe)
    }
}

private class ShoeDiffCallback : DiffUtil.ItemCallback<Shoe>() {
    override fun areItemsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
        return oldItem == newItem
    }

}