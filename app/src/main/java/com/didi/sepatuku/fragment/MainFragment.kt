package com.didi.sepatuku.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.didi.sepatuku.adapter.ListShoesAdapter
import com.didi.sepatuku.R
import com.didi.sepatuku.ShoesData
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private var list: ArrayList<Shoes> = arrayListOf()
    private var shoesData = ShoesData()
    private lateinit var fragment: Fragment

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

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

        val lists = convert(shoesData.listData)

        list.addAll(lists)
        showRecyclerList()
    }

    private fun convert(list: List<com.didi.sepatuku.Shoes>): List<Shoes> {
        val result = ArrayList<Shoes>()
        list.forEach {
            val shoes = Shoes(name = it.name, price = it.price, img = it.photo)
            result.add(shoes)
        }
        return result
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
            bundle.putString(EXTRA_NAME, shoes.name)
            val fragment = DetailShoesFragment.newInstance()
            fragment.arguments = bundle
            replace(R.id.frame_container, fragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
        const val EXTRA_NAME = "extra_name"
    }
}