package com.example.basicnotepad

import androidx.room.*

@Dao
interface TextDao {

    @Insert
    suspend fun insert(text: Text)

    @Query("SELECT * FROM text_table ORDER BY date DESC")
    suspend fun getNotes(): List<Text>

    @Query("SELECT uid FROM text_table WHERE date = :date")
    suspend fun getUid(date: String) : Int

    @Update
    suspend fun update(text: Text)

    @Delete
    suspend fun delete(text: Text)

}