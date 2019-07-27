package com.marvel.app.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

//@Dao
//interface TopicsDao {
//
//    @Insert
//    fun saveTopic(topic: Topic)
//
//    @Query("select * from topics where topicType=:topicType")
//    fun getSavedTopics(topicType: String): List<Topic>
//
//    @Delete
//    fun deleteTopic(topic: Topic)
//
//    @Query("delete from topics")
//    fun deleteAllData()
//}