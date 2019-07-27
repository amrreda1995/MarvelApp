package com.marvel.app.db.dao

import androidx.room.*
import com.marvel.app.model.ComicItemViewModel

@Dao
interface ComicDao {

    @Insert
    fun saveComic(comicItemViewModel: ComicItemViewModel)

    @Query("select * from comics where characterId=:characterId and comicsType=:comicsType and comicsType <> ''")
    fun getSavedComics(characterId: Int, comicsType: String): List<ComicItemViewModel>

    @Query("delete from comics where characterId=:characterId and comicsType=:comicsType and comicsType <> ''")
    fun deleteAllData(characterId: Int, comicsType: String)

    @Query("update comics set comicImage=:comicImage where resourceURI=:resourceURI")
    fun updateComic(resourceURI: String, comicImage: String)

    @Delete
    fun deleteComic(comicItemViewModel: ComicItemViewModel)
}