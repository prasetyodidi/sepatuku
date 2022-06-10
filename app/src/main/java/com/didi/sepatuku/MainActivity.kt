package com.didi.sepatuku

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.didi.sepatuku.activity.AboutActivity
import com.didi.sepatuku.activity.DetailShoesActivity
import com.didi.sepatuku.databinding.ActivityMainBinding
import timber.log.Timber
import com.didi.sepatuku.database.Shoes

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var list: ArrayList<Shoes> = arrayListOf()
    private var shoesData = ShoesData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        val imgLeft: ImageView = findViewById(R.id.img_left)
        imgLeft.setOnClickListener(this)

        val imgAbout: ImageView = findViewById(R.id.img_person)
        imgAbout.setOnClickListener(this)

        binding.rvShoes.setHasFixedSize(true)

        val lists = convert(shoesData.listData)

        list.addAll(lists)
        showRecyclerList()
    }

    private fun convert(list: List<com.didi.sepatuku.Shoes>): List<Shoes>{
        val result = ArrayList<Shoes>()
        list.forEach {
            val shoes = Shoes(name = it.name, price = it.price, img = it.photo)
            result.add(shoes)
        }
        return result
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_person -> {
                Intent(this, AboutActivity::class.java).also { startActivity(it) }
            }
            R.id.img_left -> {
                Toast.makeText(this, "Click icon left ", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.img_chart -> {
                Toast.makeText(this, "Click icon Chart ", Toast.LENGTH_SHORT).show()
            }
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