package com.marvel.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marvel.app.db.converters.*
import com.marvel.app.db.dao.CharacterDao
import com.marvel.app.model.Character

@Database(entities = [Character::class], version = 1)
@TypeConverters(
    CharacterComicsConverter::class,
    CharacterUrlConverter::class,
    ComicItemConverter::class,
    ComicItemsArrayListConverter::class,
    ThumbnailConverter::class,
    CharacterUrlArrayListConverter::class
)
abstract class MarvelAppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        private const val dbName = "marvel.db"
        private var dbInstance: MarvelAppDatabase? = null

        fun getInstance(context: Context): MarvelAppDatabase? {

            if (dbInstance == null) {
                synchronized(MarvelAppDatabase::class) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        MarvelAppDatabase::class.java,
                        dbName
                    ).build()
                }
            }

            return dbInstance
        }
    }
}