package com.slavakukhto.lastfm.shared.presentation.viewmodels.main

import com.badoo.reaktive.completable.CompletableObserver
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.ObservableObserver
import com.badoo.reaktive.observable.threadLocal
import com.slavakukhto.lastfm.shared.di.mainDI
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.usecases.SetUserTimeStampUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.TimestampPeriodChangedUseCase
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import org.kodein.di.instance

abstract class MainViewModel : BaseViewModel() {

    abstract fun onPeriodClicked()

    abstract fun setTimePeriod(timeStampPeriod: TimeStampPeriod)
}

class MainViewModelImpl : MainViewModel() {

    private val timestampPeriodChangedUseCase: TimestampPeriodChangedUseCase by mainDI.instance()
    private val setUserTimeStampUseCase: SetUserTimeStampUseCase by mainDI.instance()

    override fun onPeriodClicked() {
        liveData.value = MainUIData.PeriodEvent
    }

    override fun setTimePeriod(timeStampPeriod: TimeStampPeriod) {
        setUserTimeStampUseCase.execute(timeStampPeriod)
            .subscribe(object : CompletableObserver {
                override fun onComplete() = Unit

                override fun onError(error: Throwable) = Unit

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }
            })
    }

    override fun subscribe() {
        super.subscribe()
        timestampPeriodChangedUseCase.execute()
            .threadLocal()
            .subscribe(object : ObservableObserver<TimeStampPeriod> {
                override fun onComplete() = Unit

                override fun onError(error: Throwable) = Unit

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onNext(value: TimeStampPeriod) {
                    liveData.value = MainUIData.TimeData(value.period)
                }
            })
    }
}

// UIData
sealed class MainUIData : UIData {

    class TimeData(val time: String) : MainUIData()

    object PeriodEvent : MainUIData()
}
