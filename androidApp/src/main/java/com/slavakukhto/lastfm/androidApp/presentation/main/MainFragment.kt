package com.slavakukhto.lastfm.androidApp.presentation.main

import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentMainBinding
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.MainViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.MainViewModelImpl

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    override val binding: FragmentMainBinding by viewBinding { FragmentMainBinding.bind(it) }
    override val layoutId = R.layout.fragment_main
    override val viewModel: MainViewModel by lazy {
        MainViewModelImpl()
    }
}
