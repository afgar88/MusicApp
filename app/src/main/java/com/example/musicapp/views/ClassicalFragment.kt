package com.example.musicapp.views

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.databinding.FragmentClassicBinding
import com.example.musicapp.databinding.FragmentPopBinding
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongItem
import com.example.musicapp.presenters.*
import com.example.musicapp.rest.MusicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClassicFragment : BaseFragment(), ClassicalViewContract {
    val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }
    private val songAdapter by lazy {
        SongAdapter()
    }

    private val classicalPresenter: ClassicalPresenterContract by lazy {
        ClassicalPresenter(requireContext(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.classicalReciclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = songAdapter
        }
        classicalPresenter.checkNetwork()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        classicalPresenter.getClassical()

    }

    override fun onDestroy() {
        super.onDestroy()
        classicalPresenter.destroy()
    }


    override fun loadingClassical(isLoading: Boolean) {
        binding.classicalReciclerView.visibility = View.GONE
        binding.loadingImgCla.visibility = View.VISIBLE
    }

    override fun ClassicalSuccess(song: Song) {
        binding.loadingImgCla.visibility = View.GONE
        binding.classicalReciclerView.visibility = View.VISIBLE
        songAdapter.upDateData(song)
    }

    override fun ClassicalError(throwable: Throwable) {
        binding.classicalReciclerView.visibility = View.GONE
        binding.loadingImgCla.visibility = View.GONE

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

        @JvmStatic
        fun newInstance() = ClassicFragment()
    }
}

