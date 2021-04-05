package com.slavakukhto.lastfm.androidApp.presentation.auth

import android.os.Bundle
import android.view.View
import com.slavakukhto.lastfm.androidApp.BaseFragment
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.databinding.FragmentAuthBinding
import com.slavakukhto.lastfm.androidApp.helpers.*
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIDataListener
import com.slavakukhto.lastfm.shared.presentation.viewmodels.login.LoginParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.login.LoginUIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.login.LoginViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.login.LoginViewModelImpl

class AuthFragment : BaseFragment<FragmentAuthBinding, LoginViewModel>() {

    override val binding: FragmentAuthBinding by viewBinding { FragmentAuthBinding.bind(it) }
    override val layoutId = R.layout.fragment_auth
    override val viewModel: LoginViewModel by lazy {
        LoginViewModelImpl()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setUIDataListener(object : UIDataListener {
            override fun onUIDataReceived(uiData: UIData) {
                handleUIData(uiData)
            }
        })
        initViews()
    }

    private fun initViews() {
        binding.btnSignIn.setOnClickListener {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val loginParams = LoginParams(name, password)
            viewModel.signInClicked(loginParams)
        }
        binding.tvReset.setOnClickListener { viewModel.resetPasswordClicked() }
        binding.tvSignUp.setOnClickListener { viewModel.signUpClicked() }
    }

    private fun handleUIData(uiData: UIData) {
        when (val data = uiData as? LoginUIData) {
            is LoginUIData.Loading -> {
                loading(true)
            }
            is LoginUIData.Success -> {
                loading(false)
            }
            is LoginUIData.ErrorAuth -> {
                loading(false)
                requireActivity().showToast(data.message)
            }
        }
    }

    private fun loading(load: Boolean) {
        if (load) {
            binding.progressBar.makeVisible()
            binding.btnSignIn.makeDisabled()
            binding.tvSignUp.makeDisabled()
            binding.tvReset.makeDisabled()
            binding.etName.makeDisabled()
            binding.etPassword.makeDisabled()
        } else {
            binding.progressBar.makeInvisible()
            binding.btnSignIn.makeEnabled()
            binding.tvSignUp.makeEnabled()
            binding.tvReset.makeEnabled()
            binding.etName.makeEnabled()
            binding.etPassword.makeEnabled()
        }
    }
}
