package com.didi.sepatuku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.didi.sepatuku.databinding.ActivityFavoriteBinding
import com.didi.sepatuku.viewmodel.FavoriteViewModel
import timber.log.Timber

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = FavoriteViewModel(application)

        favoriteViewModel.listItems.observe(this) { listShoes ->
            listShoes.forEach {  Timber.i("$it") }
        }

    }
}