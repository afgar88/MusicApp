package com.example.musicapp.presenters

import com.example.musicapp.model.Song
import com.example.musicapp.rest.MusicRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

//class RockSongsPresenterImpl @Inject constructor(
//    private val musicRepository: MusicRepository,
//    private val disposable: CompositeDisposable
//) : RockSongsPresenter {
//
//    private var rockViewContract: RockViewContract? = null
//
//    override fun initializePresenter(viewContract: RockViewContract) {
//        rockViewContract = viewContract
//    }
//
//    override fun checkNetworkConnection() {
//        //no-op
//    }
//
//    override fun getRocksongs() {
//
////        rockViewContract?.loadingRock(true)
////
////        musicRepository.getSong().enqueue()
////            .subscribeOn(Schedulers.io())
////            .observeOn(AndroidSchedulers.mainThread())
////            .subscribe(
////                { songs -> rockViewContract?.rockSuccess(songs.Song) },
////                { error -> rockViewContract?.onError(error) }
////            )
////            .apply {
////                disposable.add(this)
////            }
//    }
//
//    override fun destroyPresenter() {
//        rockViewContract = null
//        disposable.clear()
//    }
//}
//
//
//interface RockSongsPresenter {
//    fun initializePresenter(viewContract: RockViewContract)
//    fun checkNetworkConnection()
//    fun getRocksongs()
//    fun destroyPresenter()
//}
//
//interface RockViewContract {
//    fun loadingRock(isLoading: Boolean)
//    fun rockSuccess(Song: List<Song>)
//    fun onError(error: Throwable)
//
//}