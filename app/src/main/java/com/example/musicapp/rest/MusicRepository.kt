package com.example.musicapp.rest

import com.example.musicapp.model.SongItem
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

interface MusicRepository {
    fun getRock(): Single<SongItem>
}


class MusicRepositoryImpl @Inject constructor(
    private val musicApi: MusicApi
) : MusicRepository {

    override fun getRock(): Single<SongItem> {
        return musicApi.getRockSongs()
    }
}