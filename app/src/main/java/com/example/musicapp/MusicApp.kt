//package com.example.musicapp
//
//import android.app.Application
//import com.example.musicapp.di.ApplicationModule
//import com.example.musicapp.di.DaggerMusicComponent
//import com.example.musicapp.di.MusicComponent
//
//class MusicApp : Application() {
//    override fun onCreate() {
//        super.onCreate()
//
//        musicComponent = DaggerMusicComponent
//            .builder()
//            .applicationModule(ApplicationModule(this))
//            .build()
//    }
//
//    companion object {
//        lateinit var musicComponent: MusicComponent
//    }
//}