package com.marvel.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marvel.app.model.CharacterUrl

class CharacterUrlArrayListConverter: BaseConverter<List<CharacterUrl>> {

    @TypeConverter
    override fun fromString(value: String): List<CharacterUrl> {
        val listType = object : TypeToken<List<CharacterUrl>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    override fun fromType(t: List<CharacterUrl>): String {
        return Gson().toJson(t)
    }
}