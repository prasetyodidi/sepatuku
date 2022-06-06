package com.didi.sepatuku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.didi.sepatuku.databinding.ItemRowShoesBinding

class ListShoesAdapter(private val listShoes: ArrayList<Shoes>) :
    RecyclerView.Adapter<ListShoesAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemRowShoesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shoes) {
            with(binding) {
                Glide.with(this@ListViewHolder.itemView.context)
                    .load(item.photo)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgItemPhoto)

                tvItemName.text = item.name
                tvItemPrice.text = "Rp. ${item.price}"
                this@ListViewHolder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listShoes[this@ListViewHolder.bindingAdapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_shoes, viewGroup, false)
        val binding =
            ItemRowShoesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listShoes[position])
    }

    override fun getItemCount(): Int = listShoes.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Shoes)
    }
}