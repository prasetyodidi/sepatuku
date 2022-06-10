package com.didi.sepatuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.didi.sepatuku.ListShoesAdapter
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.databinding.ActivityFavoriteBinding
import com.didi.sepatuku.viewmodel.FavoriteViewModel
import timber.log.Timber

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var list = arrayListOf<Shoes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("Favorite")

        favoriteViewModel = FavoriteViewModel(application)

        binding.rvShoes.setHasFixedSize(true)
        favoriteViewModel.getListShoes().observe(this) { listShoes ->
            listShoes.forEach { Timber.d("Shoes name: ${it.name}") }
            list.addAll(listShoes)
            showRecyclerList()
        }
    }

    private fun showRecyclerList() {
        binding.rvShoes.layoutManager = LinearLayoutManager(this)
        val listShoesAdapter = ListShoesAdapter(list)
        binding.rvShoes.adapter = listShoesAdapter

        listShoesAdapter.setOnItemClickCallback(object : ListShoesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Shoes) {
                showSelectedShoes(data)
            }
        })
    }

    private fun showSelectedShoes(shoes: Shoes) {
        val moveWithDataIntent = Intent(this, DetailShoesActivity::class.java)
        with(moveWithDataIntent) {
            putExtra(DetailShoesActivity.EXTRA_NAME, shoes.name)
            putExtra(DetailShoesActivity.EXTRA_PRICE, shoes.price)
        }
        startActivity(moveWithDataIntent)
    }
}