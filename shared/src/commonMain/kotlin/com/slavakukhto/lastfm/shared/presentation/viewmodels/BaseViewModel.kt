package com.slavakukhto.lastfm.shared.presentation.viewmodels

import com.badoo.reaktive.disposable.CompositeDisposable

abstract class BaseViewModel {

    protected val compositeDisposable = CompositeDisposable()
    protected var dataListener: UIDataListener? = null

    open fun subscribe() = Unit

    fun setUIDataListener(uiDataListener: UIDataListener) {
        dataListener = uiDataListener
    }

    fun unSubscribe() {
        compositeDisposable.clear()
    }
}
