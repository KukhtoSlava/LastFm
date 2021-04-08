package com.slavakukhto.lastfm.androidApp.presentation.main.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.ItemScrobbleFooterBinding
import com.slavakukhto.lastfm.androidApp.databinding.ItemScrobbleTrackBinding
import com.slavakukhto.lastfm.shared.domain.models.FavouriteTrack

class FavouriteTracksAdapter(private val listener: FavouriteTracksListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val TYPE_ITEM = 0
        private const val TYPE_FOOTER = 1
    }

    private var trackList = mutableListOf<FavouriteTrack>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val binding = ItemScrobbleTrackBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                TrackViewHolder(binding)
            }
            TYPE_FOOTER -> {
                val binding = ItemScrobbleFooterBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                FooterViewHolder(binding)
            }
            else -> throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
        }
    }

    override fun getItemCount(): Int {
        if (trackList.isEmpty()) {
            return 0
        }
        return trackList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == trackList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TrackViewHolder) {
            holder.bind(trackList[position])
            holder.itemView.setOnClickListener { listener.onFavouriteTrackClicked(trackList[position]) }
        } else {
            holder.itemView.setOnClickListener { listener.onFavouriteTracksMoreClicked() }
        }
    }

    fun updateList(list: List<FavouriteTrack>) {
        this.trackList.clear()
        this.trackList.addAll(list)
        notifyDataSetChanged()
    }

    class FooterViewHolder(binding: ItemScrobbleFooterBinding) : RecyclerView.ViewHolder(binding.root)

    class TrackViewHolder(
        private val binding: ItemScrobbleTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavouriteTrack) {
            with(binding) {
                tvSongName.text = item.track
                tvArtistName.text = item.artist
                tvDate.text = itemView.context.getString(
                    R.string.scrobbles_count,
                    item.scrobbles
                )
                Glide.with(binding.root)
                    .load(item.imagePath)
                    .placeholder(R.drawable.ic_empty_place)
                    .error(R.drawable.ic_empty_place)
                    .into(ivPicture)
            }
        }
    }
}
