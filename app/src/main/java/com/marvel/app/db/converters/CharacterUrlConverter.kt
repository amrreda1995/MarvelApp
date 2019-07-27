package com.marvel.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.marvel.app.model.CharacterUrl

class CharacterUrlConverter: BaseConverter<CharacterUrl> {

    @TypeConverter
    override fun fromString(value: String): CharacterUrl {
        return Gson().fromJson(value, CharacterUrl::class.java)
    }

    @TypeConverter
    override fun fromType(t: CharacterUrl): String {
        return Gson().toJson(t)
    }
}