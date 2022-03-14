package com.example.musicapp.rest

import com.example.musicapp.model.RockSongs
import com.google.gson.annotations.Since
import io.reactivex.Single
import retrofit2.http.GET

interface MusicApi {


    /**
     * Method to get rock songs from url
     */
    @GET(ROCK_SONGS)
    fun getRockSongs(): Single<RockSongs>
    companion object{
        const val BASE_URL="https://itunes.apple.com/"
        private const val ROCK_SONGS ="search?term=rock&amp;media=music&amp;entity=song&amp;limit=50"
    }
}