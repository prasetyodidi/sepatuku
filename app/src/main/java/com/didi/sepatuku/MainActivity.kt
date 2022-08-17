package com.didi.sepatuku

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.didi.sepatuku.databinding.ActivityMainBinding
import com.didi.sepatuku.presentation.about.AboutFragment
import com.didi.sepatuku.presentation.shoe.HomeFragment
import com.didi.sepatuku.presentation.shoe_favorite.FavoriteFragment
import com.didi.sepatuku.presentation.shopping_cart.ShoppingCartFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        binding.bottomNavigation.selectedItemId = R.id.action_home
        fragment = HomeFragment.newInstance()
        changeFragment(fragment)
        binding.bottomNavigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_like -> {
                    fragment = FavoriteFragment.newInstance()
                }
                R.id.action_home -> {
                    fragment = HomeFragment.newInstance()
                }
                R.id.action_cart -> {
                    fragment = ShoppingCartFragment.newInstance()
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
            val name = f.javaClass
            Timber.d("change $name")
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_about -> {
                val f = AboutFragment.newInstance()
                changeFragment(f)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}