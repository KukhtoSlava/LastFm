package com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouriteartists

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.ObservableObserver
import com.badoo.reaktive.observable.threadLocal
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.slavakukhto.lastfm.shared.di.favouritesArtistsDI
import com.slavakukhto.lastfm.shared.domain.models.FavouriteArtist
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreFavouriteArtistsUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserFavouritesArtistsUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.TimestampPeriodChangedUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import org.kodein.di.instance

abstract class FavouriteArtistsViewModel : BaseViewModel() {

    abstract fun loadFavouriteArtists()

    abstract fun onArtistClicked(favouriteArtist: FavouriteArtist)

    abstract fun onMoreClicked()
}

class FavouriteArtistsViewModelImpl : FavouriteArtistsViewModel() {

    private val getUserFavouritesArtistsUseCase: GetUserFavouritesArtistsUseCase by favouritesArtistsDI.instance()
    private val timestampPeriodChangedUseCase: TimestampPeriodChangedUseCase by favouritesArtistsDI.instance()
    private val getMoreFavouriteArtistsUrlUseCase: GetMoreFavouriteArtistsUrlUseCase by favouritesArtistsDI.instance()
    private val screenNavigator: ScreenNavigator by favouritesArtistsDI.instance()

    override fun subscribe() {
        timestampPeriodChangedUseCase.execute()
            .threadLocal()
            .subscribe(object : ObservableObserver<TimeStampPeriod> {
                override fun onComplete() = Unit

                override fun onError(error: Throwable) = Unit

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onNext(value: TimeStampPeriod) {
                    loadFavouriteArtists()
                }
            })
    }

    override fun loadFavouriteArtists() {
        getUserFavouritesArtistsUseCase.execute()
            .doOnAfterSubscribe { liveData.value = FavouriteArtistsUIData.Loading }
            .subscribe(object : SingleObserver<List<FavouriteArtist>> {
                override fun onError(error: Throwable) {
                    liveData.value = FavouriteArtistsUIData.Error(error.message)
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: List<FavouriteArtist>) {
                    liveData.value = FavouriteArtistsUIData.Success(value)
                }
            })
    }

    override fun onArtistClicked(favouriteArtist: FavouriteArtist) {
        val artistViewParams = ArtistViewParams(artist = favouriteArtist.artist)
        screenNavigator.pushScreen(Screen.ARTIST, artistViewParams, clearBackStack = false)
    }

    override fun onMoreClicked() {
        getMoreFavouriteArtistsUrlUseCase.execute()
            .subscribe(object : SingleObserver<String> {
                override fun onError(error: Throwable) {
                    liveData.value = FavouriteArtistsUIData.Error(error.message)
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
sealed class FavouriteArtistsUIData : UIData {

    object Loading : FavouriteArtistsUIData()
    class Success(val favouriteArtists: List<FavouriteArtist>) : FavouriteArtistsUIData()
    class Error(val message: String?) : FavouriteArtistsUIData()
}
