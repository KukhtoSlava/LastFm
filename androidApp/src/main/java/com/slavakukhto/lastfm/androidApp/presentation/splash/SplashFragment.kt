package com.slavakukhto.lastfm.androidApp.presentation.splash

import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentSplashBinding
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.presentation.viewmodels.splash.SplashViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.splash.SplashViewModelImpl

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val binding: FragmentSplashBinding by viewBinding { FragmentSplashBinding.bind(it) }
    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by lazy {
        SplashViewModelImpl()
    }
}
