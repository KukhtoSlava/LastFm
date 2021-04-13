package com.slavakukhto.lastfm.androidApp.presentation.track

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentTrackBinding
import com.slavakukhto.lastfm.androidApp.helpers.makeGone
import com.slavakukhto.lastfm.androidApp.helpers.makeVisible
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.TrackModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackViewModelImpl
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackViewParams

class TrackFragment : BaseFragment<FragmentTrackBinding, TrackViewModel>() {

    companion object {

        const val KEY_TRACK = "KEY_TRACK"
        const val KEY_ARTIST = "KEY_ARTIST"

        fun newInstance(params: TrackViewParams): TrackFragment {
            val fragment = TrackFragment()
            val bundle = Bundle()
            bundle.putString(KEY_TRACK, params.song)
            bundle.putString(KEY_ARTIST, params.artist)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val binding: FragmentTrackBinding by viewBinding { FragmentTrackBinding.bind(it) }
    override val layoutId = R.layout.fragment_track
    override val viewModel: TrackViewModel by lazy {
        TrackViewModelImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val track: String = arguments?.getString(KEY_TRACK, "") ?: ""
        val artist: String = arguments?.getString(KEY_ARTIST, "") ?: ""
        viewModel.setUpParams(track, artist)
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

    private fun initViews() {
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadTrack() }
        binding.ivBack.setOnClickListener { viewModel.onBackClicked() }
        binding.tvYoutube.setOnClickListener { viewModel.onPlayClicked() }
        binding.tvArtist.setOnClickListener { viewModel.onArtistClicked(binding.tvArtist.textView.text.toString()) }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? TrackUIData) {
            is TrackUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is TrackUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                setUpTrack(data.track)
            }
            is TrackUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun setUpTrack(trackModel: TrackModel) {
        binding.tvTrack.text = trackModel.trackName
        binding.tvListeners.text = trackModel.listeners.toString()
        binding.tvScrobbles.text = trackModel.scrobbles.toString()
        if (trackModel.artist.isEmpty()) {
            binding.tvArtistTitle.makeGone()
            binding.tvArtist.makeGone()
        } else {
            binding.tvArtistTitle.makeVisible()
            binding.tvArtist.makeVisible()
            binding.tvArtist.textView.text = trackModel.artist
        }
        if (trackModel.tags.isEmpty()) {
            binding.tvTagsTitle.makeGone()
            binding.tvTags.makeGone()
        } else {
            binding.tvTagsTitle.makeVisible()
            binding.tvTags.makeVisible()
            binding.tvTags.textView.text = trackModel.tags.joinToString(separator = ", ")
        }
        Glide.with(binding.root)
            .load(trackModel.imagePath)
            .placeholder(R.drawable.ic_empty_place)
            .error(R.drawable.ic_empty_place)
            .into(binding.ivTrack)
        if (trackModel.album.isEmpty()) {
            binding.tvAlbumTitle.makeGone()
            binding.tvAlbum.makeGone()
        } else {
            binding.tvAlbumTitle.makeVisible()
            binding.tvAlbum.makeVisible()
            binding.tvAlbum.textView.text = trackModel.album
        }
        if (trackModel.youtubeLink.isEmpty()) {
            binding.tvYoutube.makeGone()
            binding.tvYoutubeTitle.makeGone()
        } else {
            binding.tvYoutube.textView.text = trackModel.youtubeLink
            binding.tvYoutube.makeVisible()
            binding.tvYoutubeTitle.makeVisible()
        }
        if (trackModel.wiki.isEmpty()) {
            binding.tvWiki.makeGone()
            binding.tvWikiTitle.makeGone()
        } else {
            binding.tvWiki.makeVisible()
            binding.tvWikiTitle.makeVisible()
            binding.tvWikiTitle.text = trackModel.wiki
        }
    }
}
