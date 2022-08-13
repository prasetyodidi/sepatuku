package com.didi.sepatuku.presentation.detail_shoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.didi.sepatuku.R
import com.didi.sepatuku.databinding.FragmentDetailShoesBinding
import com.didi.sepatuku.domain.model.DetailShoe
import com.didi.sepatuku.presentation.shoe.HomeFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailShoesFragment : Fragment() {
    private var _binding: FragmentDetailShoesBinding? = null
    private val binding get() = _binding
    private val viewModel: DetailShoeViewModel by viewModel()
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.arguments

        if (bundle != null) {
            name = bundle.getString(HomeFragment.EXTRA_NAME).toString()
            Timber.d("name : $name")
            viewModel.getDetailShoe(name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailShoesBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .map { it.isLoading }
                    .distinctUntilChanged()
                    .collect {
                        showLoading(it)
                    }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .map { it.detailShoe }
                    .distinctUntilChanged()
                    .collect {
                        if (it != null) {
                            showShoeInfo(it)
                        }
                    }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .map { it.isFavorite }
                    .distinctUntilChanged()
                    .collect {
                        showFavorite(it)
                    }
            }
        }

        binding?.btnAddItem?.setOnClickListener {
            val dialog = DetailCartDialogFragment.newInstance()
            dialog.show(parentFragmentManager, EXTRA_DIALOG)
        }
        binding?.btnFavorite?.setOnClickListener {
            viewModel.changeFavorite()
        }
        binding?.btnShare?.setOnClickListener {
            Timber.d("shared!")
            Toast.makeText(activity, "Click icon Share ", Toast.LENGTH_SHORT).show()
        }

        return binding?.root
    }

    private fun showFavorite(state: Boolean) {
        if (state) {
            binding?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun showShoeInfo(detailShoe: DetailShoe) {
        binding?.run {
            Glide.with(requireContext())
                .load(detailShoe.imageUrl)
                .into(imgItemPhotoDetail)

            tvShoesPrice.text = getString(R.string.currency).plus(detailShoe.price)
            tvShoesName.text = detailShoe.name
            tvShoesDesc.text = detailShoe.desc
            Timber.d("sizes: ${detailShoe.sizes}")
            tvShoesSizes.text = detailShoe.sizes.toString()
            tvShoesStock.text = detailShoe.stock.toString()
            showFavorite(detailShoe.isFavorite)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    fun showSnackbar(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailShoesFragment()
        const val EXTRA_DIALOG = "extra_dialog"
    }
}


