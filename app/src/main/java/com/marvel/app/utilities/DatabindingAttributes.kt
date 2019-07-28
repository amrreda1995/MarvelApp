package com.marvel.app.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class DatabindingAttributes {

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageView(imageView: ImageView, imageUrl: String) {
            Picasso.get().load(imageUrl).into(imageView)
        }
    }
}