package com.slavakukhto.lastfm.androidApp.presentation.main.scrobbles

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentScrobblesBinding
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.ScrobblesTrack
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.scrobbles.ScrobblesUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.scrobbles.ScrobblesViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.scrobbles.ScrobblesViewModelImpl

class ScrobblesFragment : BaseFragment<FragmentScrobblesBinding, ScrobblesViewModel>(), ScrobblesListener {

    override val binding: FragmentScrobblesBinding by viewBinding { FragmentScrobblesBinding.bind(it) }
    override val layoutId = R.layout.fragment_scrobbles
    override val viewModel: ScrobblesViewModel by lazy {
        ScrobblesViewModelImpl()
    }
    private val scrobblesAdapter = ScrobblesAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onScrobblesTrackClicked(scrobblesTrack: ScrobblesTrack) {
        viewModel.onTrackClicked(scrobblesTrack)
    }

    override fun onScrobblesMoreClicked() {
        viewModel.onMoreClicked()
    }

    override fun handleUIData(uiData: UIData) {
        when (val data = uiData as? ScrobblesUIData) {
            is ScrobblesUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is ScrobblesUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                scrobblesAdapter.updateList(data.scrobblesTracks)
            }
            is ScrobblesUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun initViews() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.scrobblesRecyclerView.layoutManager = linearLayoutManager
        binding.scrobblesRecyclerView.adapter = scrobblesAdapter
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadScrobblesTracks() }
    }
}
