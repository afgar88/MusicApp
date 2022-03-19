package com.example.musicapp.database

import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import io.reactivex.Completable
import io.reactivex.Single

class SongRepository(val songdatabase:SongDatabase) {
    fun getSongs(gender:String):Single<List<Song>>{
        return songdatabase.songDao().listAll(gender)
    }

    fun insertSongs(songs: List<Song>):Completable{
        return songdatabase.songDao().insert(songs)
    }
}