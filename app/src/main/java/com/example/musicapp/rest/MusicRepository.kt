package com.example.musicapp.rest

import com.example.musicapp.model.SongItem
import retrofit2.Call
import javax.inject.Inject

interface MusicRepository {
    fun getRock(): Call<SongItem>
}


class MusicRepositoryImpl @Inject constructor(
    private val musicApi: MusicApi
) : MusicRepository {

    override fun getRock(): Call<SongItem> {
        return musicApi.getRockSongs()
    }
}