package com.example.musicapp.views

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.adapter.SongListener
import com.example.musicapp.databinding.FragmentPopBinding
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import com.example.musicapp.presenters.PopPresenter
import com.example.musicapp.presenters.PopPresenterContract
import com.example.musicapp.presenters.PopViewContract
import com.example.musicapp.rest.MusicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PopFragment : BaseFragment(), PopViewContract {
    val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    lateinit var songListener: SongListener
    private val songAdapter by lazy {
        SongAdapter(songListener)
    }

    private val popPresenter: PopPresenterContract by lazy {
        PopPresenter(requireContext(), this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        songListener = activity as SongListener
    }

    /**
     * This function apply the configuration to the recicler view with the linear layout manager when the view is created
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // binding = FragmentRockBinding.inflate(inflater, container, false)
        binding.popReciclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = songAdapter
        }

        popPresenter.checkNetwork()
        return binding.root
    }

    /**
     * when the fragment is on resume this function get the data
     */
    override fun onResume() {
        super.onResume()
        popPresenter.getPop()

        binding.swipePop.apply {
            setOnRefreshListener {
                popPresenter.getPop()
                isRefreshing = false
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        popPresenter.destroy()
    }

    /**
     * this function switch the visibility in the screen of the loading ico and the recycler view, hide the recycler view and show the loading image
     */
    override fun loadingPop(isLoading: Boolean) {
        binding.popReciclerView.visibility = View.GONE
        binding.loadingImgPop.visibility = View.VISIBLE
    }
    /**
     * this function switch the visibility in the screen of the loading ico and the recycler view if everything is good show the recycler and hide the loading image
     */
    override fun popSuccess(song: List<Song>) {
        binding.loadingImgPop.visibility = View.GONE
        binding.popReciclerView.visibility = View.VISIBLE
        songAdapter.upDateData(song)
    }

    /**
     * if something is wrong show in screen a error message and hide the recycler view and the loading image
     */
    override fun popError(throwable: Throwable) {
        binding.popReciclerView.visibility = View.GONE
        binding.loadingImgPop.visibility = View.GONE

        AlertDialog.Builder(requireContext())
            .setTitle("AN ERROR HAS OCCURRED")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("DISMISS") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
}