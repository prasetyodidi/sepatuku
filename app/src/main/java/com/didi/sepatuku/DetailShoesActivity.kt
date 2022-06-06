package com.didi.sepatuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class DetailShoesActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_DESCRIPTION = "extra_desc"
        const val EXTRA_SIZES = "extra_sizes"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_STOCK = "extra_stock"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shoes)

        val imgLeft: ImageView = findViewById(R.id.img_left)
        val imgPerson: ImageView = findViewById(R.id.img_person)
        imgLeft.setOnClickListener(this)
        imgPerson.setOnClickListener(this)

        val name = intent.getStringExtra(EXTRA_NAME)
        val price = intent.getIntExtra(EXTRA_PRICE, 0)
        val desc = intent.getStringExtra(EXTRA_DESCRIPTION)
        val photo = intent.getIntExtra(EXTRA_PHOTO, 0)
        val sizes = intent.getStringExtra(EXTRA_SIZES)
        val stock = intent.getIntExtra(EXTRA_STOCK, 0)

        val tvShoesName: TextView = findViewById(R.id.tv_shoes_name)
        val tvShoesPrice: TextView = findViewById(R.id.tv_shoes_price)
        val tvShoesDesc: TextView = findViewById(R.id.tv_shoes_desc)
        val tvShoesSizes: TextView = findViewById(R.id.tv_shoes_sizes)
        val imgPhoto: ImageView = findViewById(R.id.img_item_photo_detail)
        val tvStock: TextView = findViewById(R.id.tv_shoes_stock)

        Glide.with(this)
            .load(photo)
            .into(imgPhoto)

        tvShoesName.text = name
        tvShoesPrice.text = "Rp. $price"
        tvShoesSizes.text = sizes
        tvShoesDesc.text = desc
        tvStock.text = "Stock hanya $stock pasang"
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_person -> {
                Toast.makeText(this, "klick icon person ", Toast.LENGTH_SHORT).show()
                val moveIntent = Intent(this, AboutActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.img_left -> {
                Toast.makeText(this, "klick icon left ", Toast.LENGTH_SHORT).show()
                val moveIntent = Intent(this, MainActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}