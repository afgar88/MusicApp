package com.example.musicapp.views

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.adapter.SongListener
import com.example.musicapp.databinding.FragmentRockBinding
import com.example.musicapp.model.Song
import com.example.musicapp.presenters.RockPresenter
import com.example.musicapp.presenters.RockPresenterContract
import com.example.musicapp.presenters.RockViewContract


class RockFragment : BaseFragment(), RockViewContract {


    val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }

    lateinit var songListener: SongListener
    private val songAdapter by lazy {
        SongAdapter(songListener)
    }

    private val rockPresenter: RockPresenterContract by lazy {
        RockPresenter(requireContext(), this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        songListener = activity as SongListener
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

        binding.rockReciclerView.setOnClickListener() {
            Log.d("Musica", "hole")

        }

        rockPresenter.checkNetwork()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        rockPresenter.getRock()


        binding.swipeRock.apply {
            setOnRefreshListener {
                rockPresenter.getRock()
                isRefreshing = false
            }

        }


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
