package com.example.musicapp.adapter

/**
 * this interface to communicate the implementation in the MainActivity class with its call in the fragments
 */
interface SongListener {
    fun playSong(path: String)
}