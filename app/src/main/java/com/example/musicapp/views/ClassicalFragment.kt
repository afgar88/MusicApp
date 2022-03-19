package com.example.musicapp.views

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.adapter.SongListener
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

    lateinit var songListener: SongListener
    private val songAdapter by lazy {
        SongAdapter(songListener)
    }

    private val classicalPresenter: ClassicalPresenterContract by lazy {
        ClassicalPresenter(requireContext(), this)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        songListener=activity as SongListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * This function apply the configuration to the recicler view with the linear layout manager when the view is created
     */
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

    /**
     * when the fragment is on resume this function get the data
     */
    override fun onResume() {
        super.onResume()
        classicalPresenter.getClassical()

        binding.swipeClassic.apply {
            setOnRefreshListener {
                classicalPresenter.getClassical()
                isRefreshing = false
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        classicalPresenter.destroy()
    }

    /**
     * this function switch the visibility in the screen of the loading ico and the recycler view, hide the recycler view and show the loading image
     */
    override fun loadingClassical(isLoading: Boolean) {
        binding.classicalReciclerView.visibility = View.GONE
        binding.loadingImgCla.visibility = View.VISIBLE
    }

    /**
     * this function switch the visibility in the screen of the loading ico and the recycler view if everything is good show the recycler and hide the loading image
     */
    override fun classicalSuccess(song: List<Song>) {
        binding.loadingImgCla.visibility = View.GONE
        binding.classicalReciclerView.visibility = View.VISIBLE
        songAdapter.upDateData(song)
    }

    /**
     * if something is wrong show in screen a error message and hide the recycler view and the loading image
     */
    override fun classicalError(throwable: Throwable) {
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

