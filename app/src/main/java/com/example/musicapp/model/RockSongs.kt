package com.example.musicapp.model


import com.google.gson.annotations.SerializedName

data class RockSongs(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val songs: List<Song>
)