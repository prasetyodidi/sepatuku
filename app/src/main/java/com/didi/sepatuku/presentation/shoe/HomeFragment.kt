package com.didi.sepatuku.presentation.shoe

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
import com.didi.sepatuku.databinding.FragmentMainBinding
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.presentation.ShoesAdapter
import com.didi.sepatuku.presentation.detail_shoe.DetailShoesFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private val viewModel: ShoeViewModel by viewModel()
    private lateinit var adapter: ShoesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ShoesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvShoes?.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : ShoesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Shoe) {
                showSelectedShoes(data.name)
            }
        })

        binding?.rvShoes?.layoutManager = LinearLayoutManager(context)
        binding?.rvShoes?.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow
                    .map { it.shoeItems }
                    .distinctUntilChanged()
                    .collect { items ->
                        showRv(items)
                    }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateFlow
                    .map { it.isLoading }
                    .distinctUntilChanged()
                    .collect {
                        showLoading(it)
                    }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateFlow
                    .map { it.message }
                    .distinctUntilChanged()
                    .collect { message ->
                        if (message.isNotBlank()){
                            showSnackbar(message)
                        }
                    }
            }
        }
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

    private fun showRv(items: List<Shoe>) {
        if (items.isEmpty()){
            binding?.let {
                it.imageEmpty.visibility = View.VISIBLE
                it.rvShoes.visibility = View.GONE
            }
        }else {
            binding?.let {
                adapter.setData(items)
                it.imageEmpty.visibility = View.GONE
                it.rvShoes.visibility = View.VISIBLE
                it.rvShoes.layoutManager = LinearLayoutManager(requireContext())
                it.rvShoes.adapter = this.adapter
            }
        }
    }

    private fun showSelectedShoes(name: String) {
        parentFragmentManager.commit {
            val fragment = DetailShoesFragment.newInstance()
            val bundle = Bundle()
            bundle.putString(EXTRA_NAME, name)
            fragment.arguments = bundle
            replace(R.id.frame_container, fragment)
//            addToBackStack(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
        const val EXTRA_NAME = "extra_name"
    }
}