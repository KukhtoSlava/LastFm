package com.slavakukhto.lastfm.androidApp.presentation.main.profile

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentProfileBinding
import com.slavakukhto.lastfm.androidApp.helpers.TimeStampHelper
import com.slavakukhto.lastfm.androidApp.helpers.showToast
import com.slavakukhto.lastfm.androidApp.helpers.viewBinding
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.profile.ProfileUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.profile.ProfileViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.main.profile.ProfileViewModelImpl

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val binding: FragmentProfileBinding by viewBinding { FragmentProfileBinding.bind(it) }
    override val layoutId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by lazy {
        ProfileViewModelImpl()
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
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadProfile() }
        binding.tvLogOut.setOnClickListener { viewModel.logOutClicked() }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? ProfileUIData) {
            is ProfileUIData.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is ProfileUIData.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                setProfile(data.profile)
            }
            is ProfileUIData.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun setProfile(userProfile: UserProfile) {
        Glide.with(binding.root)
            .load(userProfile.profileImagePath)
            .placeholder(R.drawable.ic_empty_profile)
            .error(R.drawable.ic_empty_profile)
            .into(binding.ivProfileImage)
        binding.tvName.text = userProfile.userName
        binding.tvScrobblesCount.text = userProfile.scrobbles.toString()
        binding.tvCountryName.text = userProfile.country
        binding.tvRegistrationDate.text = TimeStampHelper.convertToReadDate(userProfile.registrationDate)
    }
}
