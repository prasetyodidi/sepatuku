package com.didi.sepatuku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListShoesAdapter(private val listShoes: ArrayList<Shoes>) :
    RecyclerView.Adapter<ListShoesAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_item_price)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_shoes, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val hero = listShoes[position]

        Glide.with(holder.itemView.context)
            .load(hero.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)

        holder.tvName.text = hero.name
        holder.tvPrice.text = "Rp. ${hero.price}"
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listShoes[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listShoes.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Shoes)
    }
}