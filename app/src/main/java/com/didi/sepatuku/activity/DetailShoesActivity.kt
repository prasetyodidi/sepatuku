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

class DetailShoesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailShoesBinding
    lateinit var shoes: Shoes
    private lateinit var detailShoesViewModel: DetailShoesViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var shoesData = ShoesData()


    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_DESCRIPTION = "extra_desc"
        const val EXTRA_SIZES = "extra_sizes"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_STOCK = "extra_stock"
    }

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

        shoes = Shoes(
            name = intent.getStringExtra(EXTRA_NAME),
            price = intent.getIntExtra(EXTRA_PRICE, 0),
            img = intent.getIntExtra(EXTRA_PHOTO, 0)
        )

        with(shoes) {
            name = intent.getStringExtra(EXTRA_NAME)
            price = intent.getIntExtra(EXTRA_PRICE, 0)
            img = intent.getIntExtra(EXTRA_PHOTO, 0)
        }


        val name = intent.getStringExtra(EXTRA_NAME)
        val shoesDetail = shoesData.listData.first { it.name.equals(name) }

        val imgPhoto: ImageView = findViewById(R.id.img_item_photo_detail)
        val tvStock: TextView = findViewById(R.id.tv_shoes_stock)

        Glide.with(this)
            .load(shoesDetail.photo)
            .into(imgPhoto)

        with(binding) {

            tvShoesName.text = name
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
                    addFavorite(shoes)
                }
            }
            R.id.btnShare -> {
                Toast.makeText(this, "Click icon Share ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addFavorite(shoes: Shoes) {
        favoriteViewModel.insert(shoes)
    }
}