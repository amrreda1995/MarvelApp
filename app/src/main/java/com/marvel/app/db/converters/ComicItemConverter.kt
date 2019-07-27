package com.marvel.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.marvel.app.model.ComicItem

class ComicItemConverter: BaseConverter<ComicItem> {

    @TypeConverter
    override fun fromString(value: String): ComicItem {
        return Gson().fromJson(value, ComicItem::class.java)
    }

    @TypeConverter
    override fun fromType(t: ComicItem): String {
        return Gson().toJson(t)
    }
}