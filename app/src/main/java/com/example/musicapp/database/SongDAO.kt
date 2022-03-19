package com.example.musicapp.database

import androidx.room.*
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface SongDAO {
    @Query("SELECT * FROM songs WHERE gender = :gender")
    fun listAll(gender:String): Single<List<Song>>

    @Query("SELECT * FROM songs WHERE primaryGenreName LIKE:primaryGenreName")
    fun listRock(primaryGenreName:String): Single<List<Song>>

//    @Query("SELECT * FROM songs WHERE title LIKE :title")
//    fun loadAllStartsWith(title: String): List<Song>

//    @Query("SELECT * FROM songs WHERE artist LIKE :name LIMIT 1")
//    fun findByName(name: String): Song

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(songs: List<Song>):Completable

    @Delete
    fun delete(songs: Song)
}