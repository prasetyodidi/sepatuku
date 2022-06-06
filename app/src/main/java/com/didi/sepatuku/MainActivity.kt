package com.didi.sepatuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var rvShoes: RecyclerView
    private var list: ArrayList<Shoes> = arrayListOf()
    private var shoesData = ShoesData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgLeft: ImageView = findViewById(R.id.img_left)
        imgLeft.setOnClickListener(this)

        val imgAbout: ImageView = findViewById(R.id.img_person)
        imgAbout.setOnClickListener(this)

        rvShoes = findViewById(R.id.rv_shoes)
        rvShoes.setHasFixedSize(true)

        list.addAll(shoesData.listData)
        showRecyclerList()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_person -> {
                val moveIntent = Intent(this, AboutActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.img_left -> {
                Toast.makeText(this, "Click icon left ", Toast.LENGTH_SHORT).show()
            }
            R.id.img_chart -> {
                Toast.makeText(this, "Click icon Chart ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerList(){
        rvShoes.layoutManager = LinearLayoutManager(this)
        val listShoesAdapter = ListShoesAdapter(list)
        rvShoes.adapter = listShoesAdapter

        listShoesAdapter.setOnItemClickCallback(object: ListShoesAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Shoes) {
                showSelectedShoes(data)
            }
        })
    }

    private fun showSelectedShoes(shoes: Shoes){
        val moveWithDataIntent = Intent(this, DetailShoesActivity::class.java)
        moveWithDataIntent.putExtra(DetailShoesActivity.EXTRA_NAME, shoes.name)
        moveWithDataIntent.putExtra(DetailShoesActivity.EXTRA_PRICE, shoes.price)
        moveWithDataIntent.putExtra(DetailShoesActivity.EXTRA_PHOTO, shoes.photo)
        moveWithDataIntent.putExtra(DetailShoesActivity.EXTRA_DESCRIPTION, shoes.description)
        moveWithDataIntent.putExtra(DetailShoesActivity.EXTRA_SIZES, shoes.sizes.toString())
        moveWithDataIntent.putExtra(DetailShoesActivity.EXTRA_STOCK, shoes.stock)
        startActivity(moveWithDataIntent)
    }
}