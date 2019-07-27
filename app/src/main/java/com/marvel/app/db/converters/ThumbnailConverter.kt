package com.marvel.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.marvel.app.model.Thumbnail

class ThumbnailConverter: BaseConverter<Thumbnail> {

    @TypeConverter
    override fun fromString(value: String): Thumbnail {
        return Gson().fromJson(value, Thumbnail::class.java)
    }

    @TypeConverter
    override fun fromType(t: Thumbnail): String {
        return Gson().toJson(t)
    }
}