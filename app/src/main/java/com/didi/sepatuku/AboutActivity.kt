package com.didi.sepatuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val imgLeft: ImageView = findViewById(R.id.img_left)
        imgLeft.setOnClickListener(this)
        val imgPerson: ImageView = findViewById(R.id.img_person)
        imgPerson.visibility = View.INVISIBLE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_person -> {
                Toast.makeText(this, "CLick icon person ", Toast.LENGTH_SHORT).show()
            }
            R.id.img_left -> {
                val moveIntent = Intent(this, MainActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

}