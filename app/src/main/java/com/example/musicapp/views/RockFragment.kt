package com.example.musicapp.views

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.databinding.FragmentRockBinding
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import com.example.musicapp.presenters.RockPresenter
import com.example.musicapp.presenters.RockPresenterContract
import com.example.musicapp.presenters.RockViewContract
import com.example.musicapp.rest.MusicService
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response


class RockFragment : BaseFragment(), RockViewContract {


    val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }

    private val songAdapter by lazy {
        SongAdapter()
    }

    private val rockPresenter: RockPresenterContract by lazy {
        RockPresenter(requireContext(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // binding = FragmentRockBinding.inflate(inflater, container, false)
        binding.rockReciclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = songAdapter
        }

        rockPresenter.checkNetwork()
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        rockPresenter.getRock()


    }

    override fun onDestroy() {
        super.onDestroy()
        rockPresenter.destroy()
    }


    override fun loadingRock(isLoading: Boolean) {
        binding.rockReciclerView.visibility = View.GONE
        binding.loadingImg.visibility = View.VISIBLE
    }


    override fun rockSuccess(song: Song) {
        binding.loadingImg.visibility = View.GONE
        binding.rockReciclerView.visibility = View.VISIBLE
        songAdapter.upDateData(song)
    }

    override fun rockError(throwable: Throwable) {
        binding.rockReciclerView.visibility = View.GONE
        binding.loadingImg.visibility = View.GONE

        AlertDialog.Builder(requireContext())
            .setTitle("AN ERROR HAS OCCURRED")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("DISMISS") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    companion object {


        fun newInstance() = RockFragment()
    }

}
