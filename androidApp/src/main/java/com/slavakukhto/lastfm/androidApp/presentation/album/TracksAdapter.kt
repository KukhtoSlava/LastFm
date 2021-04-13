package com.slavakukhto.lastfm.androidApp.presentation.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slavakukhto.lastfm.androidApp.databinding.ItemSipleTrackBinding

class TracksAdapter(private val listener: TrackListener) :
    RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    private var trackList = mutableListOf<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemSipleTrackBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun getItemCount() = trackList.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position], position + 1)
        holder.itemView.setOnClickListener { listener.onTrackClicked(trackList[position]) }
    }

    fun updateList(list: List<String>) {
        this.trackList.clear()
        this.trackList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TrackViewHolder(
        private val binding: ItemSipleTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: String, numberOfList: Int) {
            binding.tvTrack.text = ("$numberOfList. $track").toString()
        }
    }
}
