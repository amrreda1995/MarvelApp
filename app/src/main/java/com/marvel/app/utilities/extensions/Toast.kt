package com.marvel.app.utilities.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

fun Context.toast(message: String, displayLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, displayLength).show()
}

fun Context.toast(messageId: Int, displayLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, this.getString(messageId), displayLength).show()
}