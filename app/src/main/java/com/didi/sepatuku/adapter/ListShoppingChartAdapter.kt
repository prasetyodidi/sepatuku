package com.didi.sepatuku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.didi.sepatuku.R
import com.didi.sepatuku.database.ShoppingChart
import com.didi.sepatuku.databinding.ItemShoppingChartBinding

class ListShoppingChartAdapter(private val listItem: List<ShoppingChart>): RecyclerView.Adapter<ListShoppingChartAdapter.ListViewHolder>() {
    private var onDeleteClickCallback: OnDeleteClickCallback? = null
    private var onAddClickCallback: OnAddClickCallback? = null
    private var onMinClickCallback: OnMinClickCallback? = null

    inner class ListViewHolder(private val binding: ItemShoppingChartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingChart){
            with(binding){
                Glide.with(this@ListViewHolder.itemView.context)
                    .load(item.img)
                    .apply(RequestOptions().override(104,104))
                    .into(imgItem)

                textViewName.text = item.name
                textViewType.text = item.type
                textViewPrice.text = item.price.toString()
                textAmount.text = item.amount.toString()
//                spinnerListOfTypes.adapter = ArrayAdapter<Int>(
//                    this@ListViewHolder.itemView.context,
//                    R.layout.support_simple_spinner_dropdown_item,
//                    sizes
//                )
                btnDelete.setOnClickListener {
                    onDeleteClickCallback?.onDeleteClicked(item)
                }
                btnAdd.setOnClickListener {
                    onAddClickCallback?.onAddClicked(item)
                }
                btnMin.setOnClickListener {
                    onMinClickCallback?.onMinClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemShoppingChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback){
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun setOnAddClickCallback(onAddClickCallback: OnAddClickCallback){
        this.onAddClickCallback = onAddClickCallback
    }

    fun setOnMinClickCallback(onMinClickCallback: OnMinClickCallback){
        this.onMinClickCallback = onMinClickCallback
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(item: ShoppingChart)
    }

    interface OnAddClickCallback{
        fun onAddClicked(item: ShoppingChart)
    }

    interface OnMinClickCallback{
        fun onMinClicked(item: ShoppingChart)
    }
}