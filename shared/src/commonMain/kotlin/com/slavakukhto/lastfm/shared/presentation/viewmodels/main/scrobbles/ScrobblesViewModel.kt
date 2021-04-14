package com.slavakukhto.lastfm.shared.presentation.viewmodels.main.scrobbles

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.badoo.reaktive.single.threadLocal
import com.slavakukhto.lastfm.shared.di.scrobblesDI
import com.slavakukhto.lastfm.shared.domain.models.ScrobblesTrack
import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreScrobblesUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserScrobblesTracksUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackViewParams
import org.kodein.di.instance

abstract class ScrobblesViewModel : BaseViewModel() {

    abstract fun loadScrobblesTracks()

    abstract fun onTrackClicked(scrobblesTrack: ScrobblesTrack)

    abstract fun onMoreClicked()
}

class ScrobblesViewModelImpl : ScrobblesViewModel() {

    private val getUserScrobblesTracksUseCase: GetUserScrobblesTracksUseCase by scrobblesDI.instance()
    private val getMoreScrobblesUrlUseCase: GetMoreScrobblesUrlUseCase by scrobblesDI.instance()
    private val screenNavigator: ScreenNavigator by scrobblesDI.instance()

    override fun subscribe() {
        loadScrobblesTracks()
    }

    override fun loadScrobblesTracks() {
        getUserScrobblesTracksUseCase.execute()
            .threadLocal()
            .doOnAfterSubscribe { liveData.value = ScrobblesUIData.Loading }
            .subscribe(object : SingleObserver<List<ScrobblesTrack>> {
                override fun onError(error: Throwable) {
                    liveData.value = ScrobblesUIData.Error(error.message)
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: List<ScrobblesTrack>) {
                    liveData.value = ScrobblesUIData.Success(value)
                }
            })
    }

    override fun onTrackClicked(scrobblesTrack: ScrobblesTrack) {
        val trackViewParams = TrackViewParams(scrobblesTrack.track, scrobblesTrack.artist)
        screenNavigator.pushScreen(Screen.TRACK, trackViewParams, clearBackStack = false, withAnimation = true)
    }

    override fun onMoreClicked() {
        getMoreScrobblesUrlUseCase
            .execute()
            .subscribe(object : SingleObserver<String> {
                override fun onError(error: Throwable) {
                    liveData.value = ScrobblesUIData.Error(error.message)
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
sealed class ScrobblesUIData : UIData {

    object Loading : ScrobblesUIData()
    class Success(val scrobblesTracks: List<ScrobblesTrack>) : ScrobblesUIData()
    class Error(val message: String?) : ScrobblesUIData()
}
