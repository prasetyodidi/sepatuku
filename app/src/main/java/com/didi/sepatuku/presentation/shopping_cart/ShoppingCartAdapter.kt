package com.didi.sepatuku.presentation.shopping_cart

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.didi.sepatuku.R
import com.didi.sepatuku.core.util.Helper.Companion.loadImage
import com.didi.sepatuku.databinding.ItemShoppingCartBinding
import com.didi.sepatuku.domain.model.CartItem
import timber.log.Timber

class ShoppingCartAdapter : RecyclerView.Adapter<ShoppingCartAdapter.ListViewHolder>() {
    private var listItem: MutableList<CartItem> = mutableListOf()
    private var onItemCallback: OnShoppingCartItemCallback? = null

    fun setData(items: List<CartItem>) {
        val diffCallback = ShoppingCartDiffCallback(listItem, items)
        val result = DiffUtil.calculateDiff(diffCallback)
        listItem.clear()
        listItem.addAll(items)
        result.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(private val binding: ItemShoppingCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            with(binding) {
                imgItem.loadImage(item.imageUrl)

                textViewName.text = item.name
                textViewType.text = item.type
                tvPrice.text = item.price.toString()
                val amountItem: String = item.amount.toString()
                tvAmount.setText(amountItem)
                btnAdd.setOnClickListener {
                    onItemCallback?.onAddClicked(item)
                }
                tvAmount.doAfterTextChanged { text ->
                    var amount = 1
                    if (text.isNullOrBlank()){
                        try {
                            amount = text.toString().toInt()
                        } catch (e: Throwable) {
                            Timber.d("error $text ${e.message}")
                        } finally {
                            if (amount > 1) {
                                onItemCallback?.onAmountChangeCallback(item, amount)
                            } else {
                                tvAmount.setText("1")
                            }
                        }
                    }
                }
                btnDelete.setOnClickListener {
                    onItemCallback?.onDeleteClicked(item)
                }
                btnMin.setOnClickListener {
                    onItemCallback?.onMinClicked(item)
                }
                imgItem.setOnClickListener {
                    onItemCallback?.onImageClickCallback(item)
                }

                tvAmount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        wrapAmount.setBackgroundResource(R.drawable.my_primary_outline_button)
                    } else {
                        wrapAmount.setBackgroundResource(R.drawable.my_secondary_outline_button)
                    }
                }

                val amount: Int = tvAmount.text.toString().toInt()
                if (amount <= 1) {
                    btnMin.changeImageTintColour(this@ListViewHolder.itemView.context, R.color.gray)
                } else {
                    btnMin.changeImageTintColour(this@ListViewHolder.itemView.context, R.color.orange)
                }
            }
        }
    }

    fun AppCompatImageView.changeImageTintColour(context: Context, resId: Int) {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    resId
                )
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    fun setItemClickCallback(onShoppingCartItemCallback: OnShoppingCartItemCallback) {
        this.onItemCallback = onShoppingCartItemCallback
    }

    interface OnShoppingCartItemCallback {
        fun onDeleteClicked(item: CartItem)
        fun onAddClicked(item: CartItem)
        fun onMinClicked(item: CartItem)
        fun onImageClickCallback(item: CartItem)
        fun onAmountChangeCallback(item: CartItem, amount: Int)
    }
}

private class ShoppingCartDiffCallback(
    private val oldListItems: List<CartItem>,
    private val newListItems: List<CartItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListItems.size
    }

    override fun getNewListSize(): Int {
        return newListItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListItems[oldItemPosition].name == newListItems[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListItems[oldItemPosition].type == newListItems[newItemPosition].type &&
                oldListItems[oldItemPosition].price == newListItems[newItemPosition].price &&
                oldListItems[oldItemPosition].amount == newListItems[newItemPosition].amount &&
                oldListItems[oldItemPosition].imageUrl == newListItems[newItemPosition].imageUrl
    }

}