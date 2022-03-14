package com.example.musicapp.presenters

import com.example.musicapp.rest.MusicRepository
import javax.inject.Inject

class RockSongsPresenter @Inject constructor(
    private val musicRepository: MusicRepository) {

}