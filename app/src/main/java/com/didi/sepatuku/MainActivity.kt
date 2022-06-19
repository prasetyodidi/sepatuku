package com.didi.sepatuku

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.didi.sepatuku.activity.AboutActivity
import com.didi.sepatuku.databinding.ActivityMainBinding
import com.didi.sepatuku.fragment.ChartFragment
import com.didi.sepatuku.fragment.FavoriteFragment
import com.didi.sepatuku.fragment.MainFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        binding.bottomNavigation.selectedItemId = R.id.action_home
        fragment = MainFragment.newInstance()
        changeFragment(fragment)
        binding.bottomNavigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_like -> {
                    fragment = FavoriteFragment.newInstance()
                }
                R.id.action_home -> {
                    fragment = MainFragment.newInstance()
                }
                R.id.action_chart -> {
                    fragment = ChartFragment.newInstance()
                }
            }

            return@setOnItemSelectedListener changeFragment(fragment)
        }

    }

    private fun changeFragment(f: Fragment?): Boolean {
        if (f != null) {
            supportFragmentManager.commit {
                replace(R.id.frame_container, f)
            }
            return true
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_person -> {
                Intent(this, AboutActivity::class.java).also { startActivity(it) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

}