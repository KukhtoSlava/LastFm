package com.slavakukhto.lastfm.androidApp.presentation.artist

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import com.bumptech.glide.Glide
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentArtistBinding
import com.slavakukhto.lastfm.androidApp.helpers.makeGone
import com.slavakukhto.lastfm.androidApp.helpers.makeVisible
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.ArtistModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewModelImpl
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewParams

class ArtistFragment : BaseFragment<FragmentArtistBinding, ArtistViewModel>() {

    companion object {

        const val KEY_ARTIST = "KEY_ARTIST"

        fun newInstance(params: ArtistViewParams): ArtistFragment {
            val fragment = ArtistFragment()
            val bundle = Bundle()
            bundle.putString(KEY_ARTIST, params.artist)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val binding: FragmentArtistBinding by viewBinding { FragmentArtistBinding.bind(it) }
    override val layoutId = R.layout.fragment_artist
    override val viewModel: ArtistViewModel by lazy {
        ArtistViewModelImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val artist: String = arguments?.getString(KEY_ARTIST, "") ?: ""
        viewModel.setUpParams(artist)
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
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadArtist() }
        binding.ivBack.setOnClickListener { viewModel.onBackClicked() }
        binding.tvUrl.setOnClickListener { viewModel.onUrlClicked() }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? ArtistUIData) {
            is ArtistUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is ArtistUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                setUpArtist(data.artist)
            }
            is ArtistUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun setUpArtist(artistModel: ArtistModel) {
        binding.tvArtist.text = artistModel.artist
        binding.tvListeners.text = artistModel.listeners.toString()
        binding.tvScrobbles.text = artistModel.scrobbles.toString()
        if (artistModel.tags.isEmpty()) {
            binding.tvTagsTitle.makeGone()
            binding.tvTags.makeGone()
        } else {
            binding.tvTagsTitle.makeVisible()
            binding.tvTags.makeVisible()
            binding.tvTags.textView.text = artistModel.tags.joinToString(separator = ", ")
        }
        Glide.with(binding.root)
            .load(artistModel.imagePath)
            .placeholder(R.drawable.ic_empty_place)
            .error(R.drawable.ic_empty_place)
            .into(binding.ivTrack)
        if (artistModel.url.isEmpty()) {
            binding.tvUrl.makeGone()
            binding.tvUrlTitle.makeGone()
        } else {
            binding.tvUrl.textView.text = artistModel.url
            binding.tvUrl.makeVisible()
            binding.tvUrlTitle.makeVisible()
        }
        if (artistModel.wiki.isEmpty()) {
            binding.tvWiki.makeGone()
            binding.tvWikiTitle.makeGone()
        } else {
            binding.tvWiki.makeVisible()
            binding.tvWikiTitle.makeVisible()
            binding.tvWiki.textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(artistModel.wiki, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(artistModel.wiki)
            }
        }
    }
}
