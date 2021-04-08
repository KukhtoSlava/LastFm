package com.slavakukhto.lastfm.androidApp.presentation.main

import android.os.Bundle
import android.view.View
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
        MainNavigator(childFragmentManager, binding.tvHead, binding.bottomNavigationView)
    }

    private fun initViews() {
        binding.tvPeriod.setOnClickListener { viewModel.onPeriodClicked() }
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
