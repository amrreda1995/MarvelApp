package com.marvel.app.utilities.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.marvel.app.R

enum class ImageViewMode {
    NORMAL, CENTER_CROP, FIT_CENTER
}

fun ImageView.load(url: String, mode: ImageViewMode = ImageViewMode.NORMAL) {
    val load = Glide.with(context).load(url).error(R.drawable.not_found)
    when (mode) {
        ImageViewMode.NORMAL -> load.into(this)
        ImageViewMode.CENTER_CROP -> load.centerCrop().into(this)
        ImageViewMode.FIT_CENTER -> load.fitCenter().into(this)
    }
}