package com.marvel.app.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.marvel.app.utilities.extensions.load

class DatabindingAttributes {
    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageView(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl)
        }
    }
}