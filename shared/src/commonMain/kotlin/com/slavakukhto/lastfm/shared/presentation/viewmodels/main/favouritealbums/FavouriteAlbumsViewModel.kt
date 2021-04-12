package com.slavakukhto.lastfm.shared.presentation.viewmodels.main.favouritealbums

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.ObservableObserver
import com.badoo.reaktive.observable.threadLocal
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.slavakukhto.lastfm.shared.di.favouritesAlbumsDI
import com.slavakukhto.lastfm.shared.domain.models.FavouriteAlbum
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreFavouriteAlbumsUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserFavouritesAlbumsUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.TimestampPeriodChangedUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import io.ktor.utils.io.concurrent.*
import org.kodein.di.instance

abstract class FavouriteAlbumsViewModel : BaseViewModel() {

    abstract fun loadFavouriteAlbums()

    abstract fun onAlbumClicked(favouriteAlbum: FavouriteAlbum)

    abstract fun onMoreClicked()
}

class FavouriteAlbumsViewModelImpl : FavouriteAlbumsViewModel() {

    private val getUserFavouritesAlbumsUseCase: GetUserFavouritesAlbumsUseCase by favouritesAlbumsDI.instance()
    private val timestampPeriodChangedUseCase: TimestampPeriodChangedUseCase by favouritesAlbumsDI.instance()
    private val getMoreFavouriteAlbumsUrlUseCase: GetMoreFavouriteAlbumsUrlUseCase by favouritesAlbumsDI.instance()
    private val screenNavigator: ScreenNavigator by favouritesAlbumsDI.instance()

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
                    loadFavouriteAlbums()
                }
            })
    }

    override fun loadFavouriteAlbums() {
        getUserFavouritesAlbumsUseCase.execute()
            .doOnAfterSubscribe { dataListener?.onUIDataReceived(FavouriteAlbumsUIData.Loading) }
            .subscribe(object : SingleObserver<List<FavouriteAlbum>> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(FavouriteAlbumsUIData.Error(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: List<FavouriteAlbum>) {
                    dataListener?.onUIDataReceived(FavouriteAlbumsUIData.Success(value))
                }
            })
    }

    override fun onAlbumClicked(favouriteAlbum: FavouriteAlbum) {
//        screenNavigator.pushScreen()
    }

    override fun onMoreClicked() {
        getMoreFavouriteAlbumsUrlUseCase.execute()
            .subscribe(object : SingleObserver<String> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(FavouriteAlbumsUIData.Error(error.message))
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
sealed class FavouriteAlbumsUIData : UIData {

    object Loading : FavouriteAlbumsUIData()
    class Success(val favouriteAlbums: List<FavouriteAlbum>) : FavouriteAlbumsUIData()
    class Error(val message: String?) : FavouriteAlbumsUIData()
}
