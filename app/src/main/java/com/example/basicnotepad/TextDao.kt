package com.example.basicnotepad

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TextDao {

    @Insert
    suspend fun insert(text: Text)

    @Query("SELECT * FROM text_table ORDER BY uid DESC")
    suspend fun getNotes(): List<Text>

}