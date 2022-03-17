package com.example.musicapp.presenters

import android.content.Context
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import com.example.musicapp.rest.MusicService
import com.example.musicapp.rest.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ClassicalPresenter(
    private var context: Context? = null,
    private var viewContract: ClassicalViewContract? = null,
    private val networkUtils: NetworkUtils = NetworkUtils(context),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : ClassicalPresenterContract {

    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    override fun getClassical() {
        viewContract?.loadingClassical(true)
        networkUtils.networkState
            .subscribe(
                { netState ->
                    if (netState) {
                        doNetworkCall()
                    } else {
                        viewContract?.ClassicalError(Throwable("ERROR NO INTERNET CONNECTION"))
                    }
                },
                { viewContract?.ClassicalError(it) }
            ).apply {
                disposable.add(this)
            }
    }

    override fun destroy() {
        networkUtils.unregisterFromNetworkState()
        context = null
        viewContract = null
        disposable.dispose()
    }

    private fun doNetworkCall() {
        MusicService.retrofitService.getClassicalSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val song = SongItem(it.Song.size, it.Song)
                    for (i in it.Song.indices) {
                        viewContract?.ClassicalSuccess(song.Song[i])
                    }

                },
                {
                    viewContract?.ClassicalError(it)
                }
            ).apply {
                disposable.add(this)
            }
    }
}

interface ClassicalViewContract {
    fun loadingClassical(isLoading: Boolean)
    fun ClassicalSuccess(song: Song)
    fun ClassicalError(throwable: Throwable)
}

interface ClassicalPresenterContract {
    fun getClassical()
    fun destroy()
    fun checkNetwork()
}