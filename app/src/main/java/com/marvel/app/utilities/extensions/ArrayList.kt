package com.marvel.app.utilities.extensions

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}