package com.example.musicapp.rest

import com.example.musicapp.model.RockSongs
import io.reactivex.Single

interface MusicRepository {
    fun getRock(): Single<RockSongs>
}


class MusicRepositoryImpl(
    private val musicApi: MusicApi
) : MusicRepository {
    override fun getRock(): Single<RockSongs> {
        return musicApi.getRockSongs()
    }
}