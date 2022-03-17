//package com.example.musicapp.di
//
//import com.example.musicapp.presenters.RockSongsPresenter
//import com.example.musicapp.presenters.RockSongsPresenterImpl
//import com.example.musicapp.rest.MusicRepository
//import dagger.Module
//import dagger.Provides
//import io.reactivex.disposables.CompositeDisposable
//
//@Module
//class PresentersModule {
//
//    @Provides
//    fun providesCompositeDisposable() = CompositeDisposable()
//
//    @Provides
//    fun providesRockSongsPresenter(
//        musicRepository: MusicRepository,
//        disposable: CompositeDisposable
//    ): RockSongsPresenter {
//        return RockSongsPresenterImpl(musicRepository, disposable)
//
//    }
//}