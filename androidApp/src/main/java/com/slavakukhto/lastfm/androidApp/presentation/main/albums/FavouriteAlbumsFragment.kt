package com.slavakukhto.lastfm.androidApp.presentation.main.albums

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentFavouriteAlbumsBinding
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.FavouriteAlbum
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritealbums.FavouriteAlbumsUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritealbums.FavouriteAlbumsViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritealbums.FavouriteAlbumsViewModelImpl

class FavouriteAlbumsFragment : BaseFragment<FragmentFavouriteAlbumsBinding, FavouriteAlbumsViewModel>(),
    FavouriteAlbumsListener {

    override val binding: FragmentFavouriteAlbumsBinding by viewBinding { FragmentFavouriteAlbumsBinding.bind(it) }
    override val layoutId = R.layout.fragment_favourite_albums
    override val viewModel: FavouriteAlbumsViewModel by lazy {
        FavouriteAlbumsViewModelImpl()
    }
    private val favouriteAlbumsAdapter = FavouriteAlbumsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFavouriteAlbumClicked(favouriteAlbum: FavouriteAlbum) {
        viewModel.onAlbumClicked(favouriteAlbum)
    }

    override fun onFavouriteAlbumsMoreClicked() {
        viewModel.onMoreClicked()
    }

    override fun handleUIData(uiData: UIData) {
        when (val data = uiData as? FavouriteAlbumsUIData) {
            is FavouriteAlbumsUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is FavouriteAlbumsUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                favouriteAlbumsAdapter.updateList(data.favouriteAlbums)
            }
            is FavouriteAlbumsUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.favouriteAlbumsRecyclerView.layoutManager = gridLayoutManager
        binding.favouriteAlbumsRecyclerView.adapter = favouriteAlbumsAdapter
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadFavouriteAlbums() }
    }
}
