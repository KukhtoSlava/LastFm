package com.slavakukhto.lastfm.androidApp.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentMainBinding
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.MainUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.MainViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.MainViewModelImpl

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    override val binding: FragmentMainBinding by viewBinding { FragmentMainBinding.bind(it) }
    override val layoutId = R.layout.fragment_main
    override val viewModel: MainViewModel by lazy {
        MainViewModelImpl()
    }
    private lateinit var mainStateAdapter: MainStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    @SuppressLint("WrongConstant")
    private fun initViews() {
        mainStateAdapter = MainStateAdapter(this)
        binding.viewPager.adapter = mainStateAdapter
        binding.viewPager.offscreenPageLimit = MainStateAdapter.MAX_ITEMS
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvHead.text = when (position) {
                    MainStateAdapter.SCROBBLES_ITEM -> getText(R.string.scrobbles)
                    MainStateAdapter.FAVOURITE_ARTISTS_ITEM -> getText(R.string.artists)
                    MainStateAdapter.FAVOURITE_ALBUMS_ITEM -> getText(R.string.albums)
                    MainStateAdapter.FAVOURITE_TRACKS_ITEM -> getText(R.string.tracks)
                    MainStateAdapter.PROFILE_ITEM -> getText(R.string.profile)
                    else -> ""
                }
            }
        })
        binding.tvPeriod.setOnClickListener { viewModel.onPeriodClicked() }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.scrobbles -> {
                    binding.viewPager.currentItem = MainStateAdapter.SCROBBLES_ITEM
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.artists -> {
                    binding.viewPager.currentItem = MainStateAdapter.FAVOURITE_ARTISTS_ITEM
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.albums -> {
                    binding.viewPager.currentItem = MainStateAdapter.FAVOURITE_ALBUMS_ITEM
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tracks -> {
                    binding.viewPager.currentItem = MainStateAdapter.FAVOURITE_TRACKS_ITEM
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    binding.viewPager.currentItem = MainStateAdapter.PROFILE_ITEM
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? MainUIData) {
            is MainUIData.TimeData -> {
                binding.tvPeriod.text = data.time
            }
            is MainUIData.PeriodEvent -> {
                showPeriodSheet()
            }
        }
    }

    private fun showPeriodSheet() {
        val dialog = TimePeriodBottomSheet.newInstance { period ->
            viewModel.setTimePeriod(period)
        }
        dialog.show(childFragmentManager, TimePeriodBottomSheet::javaClass.name)
    }
}
