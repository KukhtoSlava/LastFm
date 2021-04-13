package com.slavakukhto.lastfm.androidApp.presentation.album

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentAlbumBinding
import com.slavakukhto.lastfm.androidApp.helpers.makeGone
import com.slavakukhto.lastfm.androidApp.helpers.makeVisible
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.AlbumModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.album.AlbumUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.album.AlbumViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.album.AlbumViewModelImpl
import com.slavakukhto.lastfm.shared.presentation.viewmodels.album.AlbumViewParams

class AlbumFragment : BaseFragment<FragmentAlbumBinding, AlbumViewModel>(), TrackListener {

    companion object {

        const val KEY_ARTIST = "KEY_ARTIST"
        const val KEY_ALBUM = "KEY_ALBUM"

        fun newInstance(params: AlbumViewParams): AlbumFragment {
            val fragment = AlbumFragment()
            val bundle = Bundle()
            bundle.putString(KEY_ARTIST, params.artist)
            bundle.putString(KEY_ALBUM, params.album)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val binding: FragmentAlbumBinding by viewBinding { FragmentAlbumBinding.bind(it) }
    override val layoutId = R.layout.fragment_album
    override val viewModel: AlbumViewModel by lazy {
        AlbumViewModelImpl()
    }
    private lateinit var tracksAdapter: TracksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val artist: String = arguments?.getString(KEY_ARTIST, "") ?: ""
        val album: String = arguments?.getString(KEY_ALBUM, "") ?: ""
        viewModel.setUpParams(album, artist)
        viewModel.setUIDataListener(object : UIDataListener {
            override fun onUIDataReceived(uiData: UIData) {
                handleUIData(uiData)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onTrackClicked(track: String) {
        val artist = binding.tvArtist.textView.text.toString()
        viewModel.onTrackClicked(artist, track)
    }

    private fun initViews() {
        tracksAdapter = TracksAdapter(this)
        binding.rwTracks.layoutManager = LinearLayoutManager(requireContext())
        binding.rwTracks.adapter = tracksAdapter
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadAlbum() }
        binding.ivBack.setOnClickListener { viewModel.onBackClicked() }
        binding.tvUrl.setOnClickListener { viewModel.onUrlClicked() }
        binding.tvArtist.setOnClickListener { viewModel.onArtistClicked(binding.tvArtist.textView.text.toString()) }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? AlbumUIData) {
            is AlbumUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is AlbumUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                setUpAlbum(data.albumModel)
            }
            is AlbumUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun setUpAlbum(albumModel: AlbumModel) {
        binding.tvAlbum.text = albumModel.album
        binding.tvListeners.text = albumModel.listeners.toString()
        binding.tvScrobbles.text = albumModel.scrobbles.toString()
        if (albumModel.tags.isEmpty()) {
            binding.tvTagsTitle.makeGone()
            binding.tvTags.makeGone()
        } else {
            binding.tvTagsTitle.makeVisible()
            binding.tvTags.makeVisible()
            binding.tvTags.textView.text = albumModel.tags.joinToString(separator = ", ")
        }
        Glide.with(binding.root)
            .load(albumModel.imagePath)
            .placeholder(R.drawable.ic_empty_place)
            .error(R.drawable.ic_empty_place)
            .into(binding.ivAlbum)
        if (albumModel.url.isEmpty()) {
            binding.tvUrl.makeGone()
            binding.tvUrlTitle.makeGone()
        } else {
            binding.tvUrl.textView.text = albumModel.url
            binding.tvUrl.makeVisible()
            binding.tvUrlTitle.makeVisible()
        }
        if (albumModel.wiki.isEmpty()) {
            binding.tvWiki.makeGone()
            binding.tvWikiTitle.makeGone()
        } else {
            binding.tvWiki.makeVisible()
            binding.tvWikiTitle.makeVisible()
            binding.tvWiki.textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(albumModel.wiki, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(albumModel.wiki)
            }
        }
        if (albumModel.artist.isEmpty()) {
            binding.tvArtistTitle.makeGone()
            binding.tvArtist.makeGone()
        } else {
            binding.tvArtistTitle.makeVisible()
            binding.tvArtist.makeVisible()
            binding.tvArtist.textView.text = albumModel.artist
        }
        if (albumModel.tracks.isEmpty()) {
            binding.topDivider.makeGone()
            binding.bottomDivider.makeGone()
            binding.tvTracksTitle.makeGone()
            binding.rwTracks.makeGone()
        } else {
            binding.topDivider.makeVisible()
            binding.bottomDivider.makeVisible()
            binding.tvTracksTitle.makeVisible()
            binding.rwTracks.makeVisible()
            tracksAdapter.updateList(albumModel.tracks)
        }
    }
}
