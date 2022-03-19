package com.example.musicapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicapp.model.Song

@Database(entities = [Song::class], version = 2)
abstract class SongDatabase : RoomDatabase() {

    abstract fun songDao(): SongDAO

    companion object {
        var INSTANCE: SongDatabase? = null
        fun getDatabase(context: Context): SongDatabase {
            if (INSTANCE == null) {
                synchronized(SongDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            SongDatabase::class.java, "song_database"
                        )
                            .build()

                    }
                }
            }
            return INSTANCE!!
        }
    }
}
