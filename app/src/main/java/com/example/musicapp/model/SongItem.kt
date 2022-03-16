package com.example.musicapp.model


import com.google.gson.annotations.SerializedName

data class SongItem(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val Song: List<Song>
)