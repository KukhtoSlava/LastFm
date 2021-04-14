package com.slavakukhto.lastfm.shared.presentation.viewmodels

import com.badoo.reaktive.disposable.CompositeDisposable
import com.slavakukhto.lastfm.shared.resolvers.livedata.KMutableLiveData

abstract class BaseViewModel {

    protected val compositeDisposable = CompositeDisposable()
    val liveData: KMutableLiveData<UIData> = KMutableLiveData()

    open fun subscribe() = Unit

    fun unSubscribe() {
        compositeDisposable.clear()
    }
}
