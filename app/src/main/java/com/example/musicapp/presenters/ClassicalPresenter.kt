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

    /**
     * This function check the network status
     */
    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    /**
     * this function subscribes to an ObservableSource and provides callbacks to handle the items,
     * if doest has a conection show a error message
     */
    override fun getClassical() {
        viewContract?.loadingClassical(true)
        networkUtils.networkState
            .subscribe(
                { netState ->
                    if (netState) {
                        doNetworkCall()
                    } else {
                        viewContract?.classicalError(Throwable("ERROR NO INTERNET CONNECTION"))
                    }
                },
                { viewContract?.classicalError(it) }
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
        MusicService.retrofitService.getClassicalSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewContract?.classicalSuccess(it.Song)

                },
                {
                    viewContract?.classicalError(it)
                }
            ).apply {
                disposable.add(this)
            }
    }
}

interface ClassicalViewContract {
    fun loadingClassical(isLoading: Boolean)
    fun classicalSuccess(song: List<Song>)
    fun classicalError(throwable: Throwable)
}

interface ClassicalPresenterContract {
    fun getClassical()
    fun destroy()
    fun checkNetwork()
}