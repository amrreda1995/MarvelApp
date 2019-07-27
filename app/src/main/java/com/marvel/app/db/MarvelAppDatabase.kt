package com.marvel.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [Topic::class, SubCategory::class], version = 1)
//abstract class MarvelAppDatabase: RoomDatabase() {
//
//    abstract fun topicsDao(): TopicsDao
//    abstract fun subCategoriesDao(): SubCategoriesDao
//
//    companion object {
//        private const val dbName = "musculi.db"
//        private var dbInstance: MarvelAppDatabase? = null
//
//        fun getInstance(context: Context): MarvelAppDatabase? {
//
//            if (dbInstance == null) {
//                synchronized(MarvelAppDatabase::class) {
//                    dbInstance = Room.databaseBuilder(
//                            context.applicationContext,
//                            MarvelAppDatabase::class.java,
//                            dbName
//                    ).build()
//                }
//            }
//
//            return dbInstance
//        }
//    }
//}