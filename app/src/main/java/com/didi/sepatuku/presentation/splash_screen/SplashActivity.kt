package com.didi.sepatuku.presentation.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.didi.sepatuku.MainActivity
import com.didi.sepatuku.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            Intent(this@SplashActivity, MainActivity::class.java).also { startActivity(it) }
            finish()
        }, 1000)

    }
}