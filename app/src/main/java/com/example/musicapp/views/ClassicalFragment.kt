package com.example.musicapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.databinding.FragmentPopBinding
import com.example.musicapp.model.SongItem
import com.example.musicapp.rest.MusicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClassicFragment : Fragment() {
    val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }
    var my_adapter = SongAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.popReciclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = my_adapter
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        MusicService.retrofitService.getClassicalSongs().enqueue(object : Callback<SongItem> {
            override fun onResponse(call: Call<SongItem>, response: Response<SongItem>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (x in it.Song.indices) {
                            my_adapter.upDateData(it.Song[x])
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SongItem>, t: Throwable) {
                //no-op
            }

        })

    }

    companion object {

        @JvmStatic
        fun newInstance() = ClassicFragment()
    }
}
