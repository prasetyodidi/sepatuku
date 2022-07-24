package com.didi.sepatuku.presentation.shoe_favorite

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
import com.didi.sepatuku.databinding.FragmentFavoriteBinding
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.presentation.ShoesAdapter
import com.didi.sepatuku.presentation.detail_shoe.DetailShoesFragment
import com.didi.sepatuku.presentation.shoe.HomeFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: ShoesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ShoesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("favorite fragment created")

        binding?.rvShoes?.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : ShoesAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Shoe) {
                showSelectedShoes(data.name)
            }
        })

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .map { it.isLoading }
                    .distinctUntilChanged()
                    .collect {
                        showLoading(it)
                    }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .map { it.items }
                    .distinctUntilChanged()
                    .collect {
                        Timber.d("fav sizes : ${it.size}")
                        showRv(it)
                    }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showSnackbar(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    private fun showRv(items: List<Shoe>) {
        binding?.let {
            adapter.setData(items)
            it.rvShoes.layoutManager = LinearLayoutManager(context)
            it.rvShoes.adapter = adapter
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}