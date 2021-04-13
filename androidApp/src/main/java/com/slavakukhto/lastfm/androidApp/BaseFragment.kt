package com.slavakukhto.lastfm.androidApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    abstract val binding: VB
    abstract val layoutId: Int
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribe()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        viewModel.unSubscribe()
        super.onDestroyView()
    }
}
