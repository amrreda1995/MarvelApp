package com.marvel.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.marvel.app.model.ComicItem
import com.google.gson.reflect.TypeToken

class ComicItemsArrayListConverter: BaseConverter<List<ComicItem>> {

    @TypeConverter
    override fun fromString(value: String): List<ComicItem> {
        val listType = object : TypeToken<List<ComicItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    override fun fromType(t: List<ComicItem>): String {
        return Gson().toJson(t)
    }
}