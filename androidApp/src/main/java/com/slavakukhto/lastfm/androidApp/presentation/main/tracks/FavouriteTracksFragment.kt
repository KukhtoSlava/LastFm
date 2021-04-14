package com.slavakukhto.lastfm.androidApp.presentation.main.tracks

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentFavouriteTracksBinding
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.FavouriteTrack
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritetracks.FavouriteTracksUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritetracks.FavouriteTracksViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritetracks.FavouriteTracksViewModelImpl

class FavouriteTracksFragment : BaseFragment<FragmentFavouriteTracksBinding, FavouriteTracksViewModel>(),
    FavouriteTracksListener {

    override val binding: FragmentFavouriteTracksBinding by viewBinding { FragmentFavouriteTracksBinding.bind(it) }
    override val layoutId = R.layout.fragment_favourite_tracks
    override val viewModel: FavouriteTracksViewModel by lazy {
        FavouriteTracksViewModelImpl()
    }
    private val favouriteTracksAdapter = FavouriteTracksAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFavouriteTrackClicked(favouriteTrack: FavouriteTrack) {
        viewModel.onTrackClicked(favouriteTrack)
    }

    override fun onFavouriteTracksMoreClicked() {
        viewModel.onMoreClicked()
    }

    override fun handleUIData(uiData: UIData) {
        when (val data = uiData as? FavouriteTracksUIData) {
            is FavouriteTracksUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is FavouriteTracksUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                favouriteTracksAdapter.updateList(data.favouriteTracks)
            }
            is FavouriteTracksUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun initViews() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.favouriteTracksRecyclerView.layoutManager = linearLayoutManager
        binding.favouriteTracksRecyclerView.adapter = favouriteTracksAdapter
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadFavouriteTracks() }
    }
}
