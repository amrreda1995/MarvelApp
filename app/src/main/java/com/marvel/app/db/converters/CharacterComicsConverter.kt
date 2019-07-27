package com.marvel.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.marvel.app.model.CharacterComics

class CharacterComicsConverter: BaseConverter<CharacterComics> {

    @TypeConverter
    override fun fromString(value: String): CharacterComics {
        return Gson().fromJson(value, CharacterComics::class.java)
    }

    @TypeConverter
    override fun fromType(t: CharacterComics): String {
        return Gson().toJson(t)
    }
}