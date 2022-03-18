package com.example.musicapp.presenters

import android.content.Context
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import com.example.musicapp.rest.MusicService
import com.example.musicapp.rest.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopPresenter(
    private var context: Context? = null,
    private var viewContract: PopViewContract? = null,
    private val networkUtils: NetworkUtils = NetworkUtils(context),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : PopPresenterContract {

    /**
     * This function check the network status
     */
    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    /**
     * This function check the network status
     */

    override fun getPop() {
        viewContract?.loadingPop(true)
        networkUtils.networkState
            .subscribe(
                { netState ->
                    if (netState) {
                        doNetworkCall()
                    } else {
                        viewContract?.popError(Throwable("ERROR NO INTERNET CONNECTION"))
                    }
                },
                { viewContract?.popError(it) }
            ).apply {
                disposable.add(this)
            }
    }
    /**
     * this functionvun register the network state, destroy the context, the view contract and dispose them
     */

    override fun destroy() {
        networkUtils.unregisterFromNetworkState()
        context = null
        viewContract = null
        disposable.dispose()
    }

    /**
     * this function confirm the network works an call it
     */

    private fun doNetworkCall() {
        MusicService.retrofitService.getPopSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val song = SongItem(it.Song.size, it.Song)
                    for (i in it.Song.indices) {
                        viewContract?.popSuccess(song.Song[i])
                    }

                },
                {
                    viewContract?.popError(it)
                }
            ).apply {
                disposable.add(this)
            }
    }
}

interface PopViewContract {
    fun loadingPop(isLoading: Boolean)
    fun popSuccess(song: Song)
    fun popError(throwable: Throwable)
}

interface PopPresenterContract {
    fun getPop()
    fun destroy()
    fun checkNetwork()
}