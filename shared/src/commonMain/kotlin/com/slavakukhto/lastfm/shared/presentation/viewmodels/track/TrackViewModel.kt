package com.slavakukhto.lastfm.shared.presentation.viewmodels.track

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.badoo.reaktive.single.threadLocal
import com.slavakukhto.lastfm.shared.di.trackDI
import com.slavakukhto.lastfm.shared.domain.models.TrackModel
import com.slavakukhto.lastfm.shared.domain.usecases.GetTrackModelUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.album.AlbumViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.youtube.YouTubeScreenParams
import org.kodein.di.instance

abstract class TrackViewModel : BaseViewModel() {

    abstract fun setUpParams(track: String, artist: String)

    abstract fun onArtistClicked(artist: String)

    abstract fun onAlbumClicked(artist: String, album: String)

    abstract fun onBackClicked()

    abstract fun onYouTubeClicked(link: String)

    abstract fun onLastFmClicked(link: String)

    abstract fun loadTrack()
}

class TrackViewModelImpl : TrackViewModel() {

    private val screenNavigator: ScreenNavigator by trackDI.instance()
    private val getTrackModelUseCase: GetTrackModelUseCase by trackDI.instance()
    private var track: String = ""
    private var artist: String = ""

    override fun setUpParams(track: String, artist: String) {
        this.track = track
        this.artist = artist
    }

    override fun subscribe() {
        super.subscribe()
        loadTrack()
    }

    override fun onBackClicked() {
        screenNavigator.popScreen()
    }

    override fun onArtistClicked(artist: String) {
        val artistViewParams = ArtistViewParams(artist = artist)
        screenNavigator.pushScreen(Screen.ARTIST, artistViewParams, clearBackStack = false, withAnimation = true)
    }

    override fun onAlbumClicked(artist: String, album: String) {
        val albumViewParams = AlbumViewParams(album = album, artist = artist)
        screenNavigator.pushScreen(Screen.ALBUM, albumViewParams, clearBackStack = false, withAnimation = true)
    }

    override fun onYouTubeClicked(link: String) {
        val youtubeScreenParams = YouTubeScreenParams(url = link)
        screenNavigator.pushScreen(Screen.YOUTUBE, youtubeScreenParams)
    }

    override fun onLastFmClicked(link: String) {
        val browserScreenParams = BrowserScreenParams(url = link)
        screenNavigator.pushScreen(Screen.BROWSER, browserScreenParams)
    }

    override fun loadTrack() {
        if (track.isEmpty() || artist.isEmpty()) {
            liveData.value = TrackUIData.Error(null)
            return
        }
        getTrackModelUseCase.execute(track, artist)
            .doOnAfterSubscribe { liveData.value = TrackUIData.Loading }
            .threadLocal()
            .subscribe(object : SingleObserver<TrackModel> {
                override fun onError(error: Throwable) {
                    liveData.value = TrackUIData.Error(error.message)
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: TrackModel) {
                    liveData.value = TrackUIData.Success(value)
                }
            })
    }
}

// UIData
sealed class TrackUIData : UIData {

    object Loading : TrackUIData()
    class Success(val track: TrackModel) : TrackUIData()
    class Error(val message: String?) : TrackUIData()
}
