package com.example.musicapp.model


import com.google.gson.annotations.SerializedName

/**
 * here is the list of the songs
 */

data class SongItem(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val Song: List<Song>
)