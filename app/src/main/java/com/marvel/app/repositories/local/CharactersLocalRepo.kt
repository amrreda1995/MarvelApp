package com.marvel.app.repositories.local

import android.content.Context
import com.marvel.app.db.MarvelAppDatabase
import com.marvel.app.model.Character
import javax.inject.Inject

interface CharactersLocalRepoInterface {

    suspend fun saveCharacter(character: Character)
    suspend fun getSavedCharacter(): List<Character>
    suspend fun deleteCharacter(character: Character)
    suspend fun deleteAllData()
}

class CharactersLocalRepo @Inject constructor(context: Context) : CharactersLocalRepoInterface {

    private val marvelDatabase = MarvelAppDatabase.getInstance(context)
    private val characterDao = marvelDatabase?.characterDao()

    override suspend fun saveCharacter(character: Character) {
        characterDao?.saveCharacter(character)
    }

    override suspend fun getSavedCharacter(): List<Character> {
        characterDao?.let {
            return it.getSavedCharacter()
        } ?: run {
            return listOf()
        }
    }

    override suspend fun deleteCharacter(character: Character) {
        characterDao?.deleteCharacter(character)
    }

    override suspend fun deleteAllData() {
        characterDao?.deleteAllData()
    }
}