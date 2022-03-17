package com.example.musicapp.rest

import com.example.musicapp.model.SongItem
import io.reactivex.Single

import retrofit2.Call

import retrofit2.http.GET

interface MusicApi {


    /**
     * Method to get Song songs from url
     */
    @GET(ROCK_SONGS)
    fun getRockSongs(): Single<SongItem>

    @GET(POP_SONGS)
    fun getPopSongs(): Single<SongItem>

    @GET(CLASSICAL_SONGS)
    fun getClassicalSongs(): Single<SongItem>


    companion object{
        const val BASE_URL="https://itunes.apple.com/"
        private const val ROCK_SONGS ="search?term=rock&amp;media=music&amp;entity=song&amp;limit=50"

        private const val POP_SONGS="search?term=pop&amp;media=music&amp;entity=song&amp;limit=50"

        private const val CLASSICAL_SONGS="search?term=classick&amp;media=music&amp;entity=song&amp;limit=50"


    }


}