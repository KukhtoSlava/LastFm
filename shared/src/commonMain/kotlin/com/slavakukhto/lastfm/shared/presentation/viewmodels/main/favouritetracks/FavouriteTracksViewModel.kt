package com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritetracks

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.ObservableObserver
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.slavakukhto.lastfm.shared.di.favouritesTracksDI
import com.slavakukhto.lastfm.shared.domain.models.FavouriteTrack
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreFavouriteTracksUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserFavouritesTracksUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.TimestampPeriodChangedUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import org.kodein.di.instance

abstract class FavouriteTracksViewModel : BaseViewModel() {

    abstract fun loadFavouriteTracks()

    abstract fun onTrackClicked(favouriteTrack: FavouriteTrack)

    abstract fun onMoreClicked()
}

class FavouriteTracksViewModelImpl : FavouriteTracksViewModel() {

    private val getUserFavouritesTracksUseCase: GetUserFavouritesTracksUseCase by favouritesTracksDI.instance()
    private val timestampPeriodChangedUseCase: TimestampPeriodChangedUseCase by favouritesTracksDI.instance()
    private val getMoreFavouriteTracksUrlUseCase: GetMoreFavouriteTracksUrlUseCase by favouritesTracksDI.instance()
    private val screenNavigator: ScreenNavigator by favouritesTracksDI.instance()

    override fun subscribe() {
        timestampPeriodChangedUseCase.execute()
            .subscribe(object : ObservableObserver<TimeStampPeriod> {
                override fun onComplete() = Unit

                override fun onError(error: Throwable) = Unit

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onNext(value: TimeStampPeriod) {
                    loadFavouriteTracks()
                }
            })
    }

    override fun loadFavouriteTracks() {
        getUserFavouritesTracksUseCase.execute()
            .doOnAfterSubscribe { dataListener?.onUIDataReceived(FavouriteTracksUIData.Loading) }
            .subscribe(object : SingleObserver<List<FavouriteTrack>> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(FavouriteTracksUIData.Error(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: List<FavouriteTrack>) {
                    dataListener?.onUIDataReceived(FavouriteTracksUIData.Success(value))
                }
            })
    }

    override fun onTrackClicked(favouriteTrack: FavouriteTrack) {
//        screenNavigator.pushScreen()
    }

    override fun onMoreClicked() {
        getMoreFavouriteTracksUrlUseCase
            .execute()
            .subscribe(object : SingleObserver<String> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(FavouriteTracksUIData.Error(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: String) {
                    val params = BrowserScreenParams(value)
                    screenNavigator.pushScreen(Screen.BROWSER, params)
                }
            })
    }
}

// UIData
sealed class FavouriteTracksUIData : UIData {

    object Loading : FavouriteTracksUIData()
    class Success(val favouriteTracks: List<FavouriteTrack>) : FavouriteTracksUIData()
    class Error(val message: String?) : FavouriteTracksUIData()
}
