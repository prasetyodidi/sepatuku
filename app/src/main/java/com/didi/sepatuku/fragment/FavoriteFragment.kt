package com.didi.sepatuku.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.didi.sepatuku.adapter.ListShoesAdapter
import com.didi.sepatuku.R
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.databinding.FragmentFavoriteBinding
import com.didi.sepatuku.viewmodel.FavoriteViewModel
import timber.log.Timber

class FavoriteFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var list = arrayListOf<Shoes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        favoriteViewModel = FavoriteViewModel(requireActivity().application)

        binding?.rvShoes?.setHasFixedSize(true)
        favoriteViewModel.getListShoes().observe(viewLifecycleOwner) { listShoes ->
            Timber.d("observe: ${listShoes.size}")
            listShoes.forEach { Timber.d("Shoes name: ${it.name}") }
            list.addAll(listShoes)
            showRecyclerList()
        }
    }

    private fun showRecyclerList() {
        binding?.rvShoes?.layoutManager = LinearLayoutManager(context)
        val listShoesAdapter = ListShoesAdapter(list)
        binding?.rvShoes?.adapter = listShoesAdapter

        listShoesAdapter.setOnItemClickCallback(object : ListShoesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Shoes) {
                showSelectedShoes(data)
            }
        })
    }

    private fun showSelectedShoes(shoes: Shoes) {
        parentFragmentManager.commit {
            val bundle = Bundle()
            bundle.putString(MainFragment.EXTRA_NAME, shoes.name)
            val fragment = DetailShoesFragment.newInstance()
            fragment.arguments = bundle
            replace(R.id.frame_container, fragment)
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