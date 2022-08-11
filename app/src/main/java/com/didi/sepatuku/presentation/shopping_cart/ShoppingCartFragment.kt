package com.didi.sepatuku.presentation.shopping_cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.didi.sepatuku.R
import com.didi.sepatuku.databinding.FragmentShoppingCartBinding
import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.presentation.detail_shoe.DetailShoesFragment
import com.didi.sepatuku.presentation.shoe.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ShoppingCartFragment : Fragment() {
    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding
    private val viewModel: ShoppingCartViewModel by viewModel()
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ShoppingCartAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state
                    .map { it.isLoading }
                    .distinctUntilChanged()
                    .collect {
                        showLoading(it)
                    }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state
                    .map { it.message }
                    .distinctUntilChanged()
                    .collect {
                        it?.let {
                            showSnackbar(it)
                        }
                    }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state
                    .map { it.items }
                    .distinctUntilChanged()
                    .collect {
                        Timber.d("shopping cart sizes : ${it.size}")
                        showRv(it)
                    }
            }
        }

        adapter.setItemClickCallback(object : ShoppingCartAdapter.OnShoppingCartItemCallback{
            override fun onDeleteClicked(item: CartItem) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Are you sure delete this item?")
                    .setNeutralButton("cancel"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("delete"){ _, _ ->
                        Timber.d("onDelete ${item.name}")
                        viewModel.deleteItemFromShoppingCart(item)
                    }
                    .show()
            }

            override fun onAddClicked(item: CartItem) {
                viewModel.updateAmount(item, item.amount+1)
            }

            override fun onMinClicked(item: CartItem) {
                viewModel.updateAmount(item, item.amount-1)
            }

            override fun onImageClickCallback(item: CartItem) {
                showSelectedShoes(item.name)
            }

            override fun onAmountChangeCallback(item: CartItem, amount: Int) {
                Timber.d("amount change $amount")
                viewModel.updateAmount(item, amount)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun showSnackbar(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    private fun showRv(items: List<CartItem>) {
        if (items.isEmpty()){
            binding?.let {
                it.imageEmpty.visibility = View.VISIBLE
                it.rvCart.visibility = View.GONE
            }
        }else {
            binding?.let {
                adapter.setData(items.asReversed())
                it.imageEmpty.visibility = View.GONE
                it.rvCart.visibility = View.VISIBLE
                it.rvCart.layoutManager = LinearLayoutManager(requireContext())
                it.rvCart.adapter = this.adapter
            }
        }
    }

    private fun showSelectedShoes(name: String) {
        parentFragmentManager.commit {
            val fragment = DetailShoesFragment.newInstance()
            val bundle = Bundle()
            bundle.putString(HomeFragment.EXTRA_NAME, name)
            fragment.arguments = bundle
            replace(R.id.frame_container, fragment)
            addToBackStack(null)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingCartFragment()
    }
}