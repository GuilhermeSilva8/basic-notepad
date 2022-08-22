package com.example.basicnotepad

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Text::class], version = 3)
abstract class TextAppDatabase : RoomDatabase() {

    abstract fun textDao(): TextDao

    companion object {

        @Volatile
        private var INSTANCE: TextAppDatabase? = null

        fun getDatabase(context: Context): TextAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TextAppDatabase::class.java,
                    "text_database"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

}