package com.didi.sepatuku.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.didi.sepatuku.databinding.ItemRowShoesBinding
import com.didi.sepatuku.database.Shoes

class ListShoesAdapter(private val listShoes: ArrayList<Shoes>) :
    RecyclerView.Adapter<ListShoesAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemRowShoesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Shoes) {
            with(binding) {
                Glide.with(this@ListViewHolder.itemView.context)
                    .load(item.img)
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
        val binding = ItemRowShoesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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