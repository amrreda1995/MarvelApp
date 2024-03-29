package com.marvel.app.utilities.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, displayLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, displayLength).show()
}