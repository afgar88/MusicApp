package com.example.musicapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.model.Song
import com.squareup.picasso.Picasso
import java.util.*

class SongAdapter(
    private var songListener: SongListener,
    private val songList: MutableList<Song> = mutableListOf()
) : RecyclerView.Adapter<SongViewAdapter>() {


    /**
     * This funtion add an element to the list, notify the position where the item is
     * inserted and Notify any registered observers that the data set has changed.
     */
    fun upDateData(Song: Song) {
        songList.add(Song)
        notifyItemInserted(0)
        notifyDataSetChanged()

    }

    /**
     * this function create a new ViewHolder and initializes some private fields
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewAdapter {
        val songItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewAdapter(songItem)
    }

    /**
     * this function update the ViewHolder contents with the item at the given position
     */

    override fun onBindViewHolder(adapter: SongViewAdapter, position: Int) {
        val my_song = songList[position]
        adapter.bind(my_song)
        adapter.itemView.setOnClickListener{
            songListener.playSong(songList[position].previewUrl)

        }
    }

    /**
     * This function count the elements added to the list
     */

    override fun getItemCount(): Int = songList.size
}

class SongViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.song_name)
    private val artist: TextView = itemView.findViewById(R.id.song_artist)
    private val collection: TextView = itemView.findViewById(R.id.song_colection)
    private val image: ImageView = itemView.findViewById(R.id.song_image)
    private val price:TextView=itemView.findViewById(R.id.song_price)

    /**
     * this funtion bind the data with the view
     */

    fun bind(songInfo: Song) {
        title.text = songInfo.trackName
        artist.text = songInfo.artistName
        collection.text = songInfo.collectionName
        price.text=songInfo.trackPrice.toString()+" USD"


        Picasso.get().load(songInfo.artworkUrl100)
            .placeholder(R.drawable.ic_baseline_photo_camera_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .resize(80, 80)
            .into(image)
    }


}