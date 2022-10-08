package com.didi.sepatuku.core.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

class Helper {
    companion object {
        fun AppCompatImageView.loadImage(url: String){
            Glide.with(this)
                .load(url)
                .into(this)
        }
    }
}