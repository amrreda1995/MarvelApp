package com.marvel.app.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.marvel.app.model.ComicItemViewModel

@Dao
interface ComicDao {

    @Insert
    fun saveComic(comicItemViewModel: ComicItemViewModel)

    @Query("select * from comics where characterId=:characterId and comicsType=:comicsType and id <> 0")
    fun getSavedComics(characterId: Int, comicsType: String): List<ComicItemViewModel>

    @Delete
    fun deleteComic(comicItemViewModel: ComicItemViewModel)

    @Query("delete from comics where characterId=:characterId and comicsType=:comicsType and id <> 0")
    fun deleteAllData(characterId: Int, comicsType: String)
}