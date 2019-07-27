package com.marvel.app.db.converters

interface BaseConverter <T> {

    fun fromString(value: String): T
    fun fromType(t: T): String
}