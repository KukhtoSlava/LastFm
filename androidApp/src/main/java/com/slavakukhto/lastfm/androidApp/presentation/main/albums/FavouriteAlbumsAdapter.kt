package com.slavakukhto.lastfm.androidApp.presentation.main.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.ItemFavouriteAlbumBinding
import com.slavakukhto.lastfm.androidApp.databinding.ItemFavouriteFooterBinding
import com.slavakukhto.lastfm.shared.domain.models.FavouriteAlbum

class FavouriteAlbumsAdapter(private val listener: FavouriteAlbumsListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val TYPE_ITEM = 0
        private const val TYPE_FOOTER = 1
    }

    private var albumsList = mutableListOf<FavouriteAlbum>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val binding = ItemFavouriteAlbumBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                AlbumViewHolder(binding)
            }
            TYPE_FOOTER -> {
                val binding = ItemFavouriteFooterBinding.inflate(
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
        if (albumsList.isEmpty()) {
            return 0
        }
        return albumsList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == albumsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlbumViewHolder) {
            holder.bind(albumsList[position])
            holder.itemView.setOnClickListener { listener.onFavouriteAlbumClicked(albumsList[position]) }
        } else {
            holder.itemView.setOnClickListener { listener.onFavouriteAlbumsMoreClicked() }
        }
    }

    fun updateList(list: List<FavouriteAlbum>) {
        this.albumsList.clear()
        this.albumsList.addAll(list)
        notifyDataSetChanged()
    }

    class FooterViewHolder(binding: ItemFavouriteFooterBinding) : RecyclerView.ViewHolder(binding.root)

    class AlbumViewHolder(
        private val binding: ItemFavouriteAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavouriteAlbum) {
            with(binding) {
                tvAlbumName.text = item.album
                tvScrobbles.text = itemView.context.getString(
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
