package com.didi.sepatuku

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.didi.sepatuku.activity.AboutActivity
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.databinding.ActivityMainBinding
import com.didi.sepatuku.fragment.ChartFragment
import com.didi.sepatuku.fragment.FavoriteFragment
import com.didi.sepatuku.fragment.MainFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        val imgAbout: ImageView = findViewById(R.id.img_person)
        imgAbout.setOnClickListener(this)

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

    private fun addChip(value: Int): Chip{
        val chip = Chip(this)
        chip.text = value.toString()
        return chip
    }

    @SuppressLint("SetTextI18n")
    fun showBottomSheet(stateParam: Boolean, shoes: Shoes, sizes: List<Int> = listOf(0)) {
        var bottomSheet: BottomSheetBehavior<*>
        Timber.d("show bottom sheet")
        with(binding) {
            bottomSheet = BottomSheetBehavior.from(cvBottomSheet)
            if (stateParam) {
                cvBottomSheet.visibility = View.VISIBLE
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
                Glide.with(this@MainActivity)
                    .load(shoes.img)
                    .into(imgItem)
                textPrice.text = "Rp ${shoes.price}"
                textSize.text = sizes[0].toString()
                for (i in sizes){
                    chipsTypes.addView(addChip(i))
                }
                chipsTypes.setOnCheckedStateChangeListener { group, checkedIds ->
                    Timber.d("checkedId: $checkedIds")
                    chipsTypes.getChildAt(checkedIds[0])
                }
            } else {
                bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            }
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

}