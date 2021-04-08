package com.slavakukhto.lastfm.androidApp.presentation.main.artists

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentFavouriteArtistsBinding
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.FavouriteArtist
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouriteartists.FavouriteArtistsUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouriteartists.FavouriteArtistsViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouriteartists.FavouriteArtistsViewModelImpl

class FavouriteArtistsFragment : BaseFragment<FragmentFavouriteArtistsBinding, FavouriteArtistsViewModel>(),
    FavouriteArtistsListener {

    override val binding: FragmentFavouriteArtistsBinding by viewBinding { FragmentFavouriteArtistsBinding.bind(it) }
    override val layoutId = R.layout.fragment_favourite_artists
    override val viewModel: FavouriteArtistsViewModel by lazy {
        FavouriteArtistsViewModelImpl()
    }
    private val favouriteArtistsAdapter = FavouriteArtistsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUIDataListener(object : UIDataListener {
            override fun onUIDataReceived(uiData: UIData) {
                handleUIData(uiData)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFavouriteArtistClicked(favouriteArtist: FavouriteArtist) {
        viewModel.onArtistClicked(favouriteArtist)
    }

    override fun onFavouriteArtistsMoreClicked() {
        viewModel.onMoreClicked()
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.favouriteArtistsRecyclerView.layoutManager = gridLayoutManager
        binding.favouriteArtistsRecyclerView.adapter = favouriteArtistsAdapter
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadFavouriteArtists() }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? FavouriteArtistsUIData) {
            is FavouriteArtistsUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is FavouriteArtistsUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                favouriteArtistsAdapter.updateList(data.favouriteArtists)
            }
            is FavouriteArtistsUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }
}
