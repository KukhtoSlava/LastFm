package com.slavakukhto.lastfm.shared.presentation.viewmodels

import com.badoo.reaktive.disposable.CompositeDisposable

abstract class BaseViewModel {

    protected val compositeDisposable = CompositeDisposable()

    abstract fun subscribe(data: ((UIData) -> Unit?)? = null)

    fun unSubscribe() {
        compositeDisposable.clear()
    }
}
