package com.example.basicnotepad

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TextDao {

    @Insert
    suspend fun insert(text: Text)

}