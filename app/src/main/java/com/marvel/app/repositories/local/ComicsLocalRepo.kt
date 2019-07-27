package com.marvel.app.repositories.local

import android.content.Context
import com.marvel.app.db.MarvelAppDatabase
import com.marvel.app.model.ComicItemViewModel
import javax.inject.Inject

interface ComicsLocalRepoInterface {

    suspend fun saveComic(comicItemViewModel: ComicItemViewModel)
    suspend fun getSavedComics(characterId: Int, comicsType: String): List<ComicItemViewModel>
    suspend fun deleteComic(comicItemViewModel: ComicItemViewModel)
    suspend fun deleteAllData(characterId: Int, comicsType: String)
}

class ComicsLocalRepo @Inject constructor(context: Context) : ComicsLocalRepoInterface {

    private val marvelDatabase = MarvelAppDatabase.getInstance(context)
    private val comicDao = marvelDatabase?.comicDao()

    override suspend fun saveComic(comicItemViewModel: ComicItemViewModel) {
        comicDao?.saveComic(comicItemViewModel)
    }

    override suspend fun getSavedComics(characterId: Int, comicsType: String): List<ComicItemViewModel> {
        comicDao?.let {
            return it.getSavedComics(characterId, comicsType)
        } ?: run {
            return listOf()
        }
    }

    override suspend fun deleteComic(comicItemViewModel: ComicItemViewModel) {
        comicDao?.saveComic(comicItemViewModel)
    }

    override suspend fun deleteAllData(characterId: Int, comicsType: String) {
        comicDao?.deleteAllData(characterId, comicsType)
    }
}