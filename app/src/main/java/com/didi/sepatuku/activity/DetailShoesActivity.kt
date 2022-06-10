package com.didi.sepatuku.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.didi.sepatuku.MainActivity
import com.didi.sepatuku.R
import com.didi.sepatuku.ShoesData
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.databinding.ActivityDetailShoesBinding
import com.didi.sepatuku.viewmodel.DetailShoesViewModel
import com.didi.sepatuku.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import com.didi.sepatuku.Shoes as ShoesDetail

class DetailShoesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailShoesBinding
    private lateinit var detailShoesViewModel: DetailShoesViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var shoesDetail: ShoesDetail
    private var shoesData = ShoesData()
    private var shoes: Shoes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailShoesViewModel = DetailShoesViewModel(application)
        favoriteViewModel = FavoriteViewModel(application)

        val imgLeft: ImageView = findViewById(R.id.img_left)
        val imgPerson: ImageView = findViewById(R.id.img_person)
        imgLeft.setOnClickListener(this)
        imgPerson.setOnClickListener(this)

        val imgPhoto: ImageView = findViewById(R.id.img_item_photo_detail)
        val tvStock: TextView = findViewById(R.id.tv_shoes_stock)

        val name = intent.getStringExtra(EXTRA_NAME)
        shoesDetail = shoesData.listData.first { it.name.equals(name) }
        setShoes(shoesDetail)

        Glide.with(this)
            .load(shoesDetail.photo)
            .into(imgPhoto)

        with(binding) {

            tvShoesName.text = shoesDetail.name
            tvShoesPrice.text = "Rp. ${shoesDetail.price}"
            tvShoesSizes.text = shoesDetail.sizes.toString()
            tvShoesDesc.text = shoesDetail.description
            tvStock.text = "Stock hanya ${shoesDetail.stock} pasang"

            btnFavorite.setOnClickListener(this@DetailShoesActivity)
            btnShare.setOnClickListener(this@DetailShoesActivity)
        }

        binding.bottomNavigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_like -> {
                    Toast.makeText(this, "Like this shoe", Toast.LENGTH_SHORT).show()
                    Intent(this, FavoriteActivity::class.java).also { startActivity(it) }
                }
                R.id.action_home -> {
                    Intent(this, MainActivity::class.java).also { startActivity(it) }
                }
                R.id.action_about -> {
                    Intent(this, AboutActivity::class.java).also { startActivity(it) }
                    finish()
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_person -> {
                Toast.makeText(this, "Click icon person ", Toast.LENGTH_SHORT).show()
                val moveIntent = Intent(this, AboutActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.img_chart -> {
                Toast.makeText(this, "Click icon Chart ", Toast.LENGTH_SHORT).show()
            }

            R.id.img_left -> {
                Toast.makeText(this, "Click icon left ", Toast.LENGTH_SHORT).show()
                val moveIntent = Intent(this, MainActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.btnFavorite -> {
                Toast.makeText(this, "Click icon favorite ", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    Timber.i("${Thread.currentThread()}")
                    shoes?.let { addFavorite(it) }
                }
            }
            R.id.btnShare -> {
                Toast.makeText(this, "Click icon Share ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setShoes(detailShoes: ShoesDetail){
        shoes?.name = detailShoes.name
        shoes?.price = detailShoes.price
        shoes?.img = detailShoes.photo
    }

    private fun addFavorite(shoes: Shoes) {
        favoriteViewModel.insert(shoes)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}