package com.marvel.app.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.marvel.app.model.Character

@Dao
interface CharacterDao {

    @Insert
    fun saveCharacter(character: Character)

    @Query("select * from characters where id <> 0")
    fun getSavedCharacter(): List<Character>

    @Delete
    fun deleteCharacter(character: Character)

    @Query("delete from characters")
    fun deleteAllData()
}