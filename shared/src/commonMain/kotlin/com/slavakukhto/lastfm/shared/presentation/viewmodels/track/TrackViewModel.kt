package com.slavakukhto.lastfm.shared.presentation.viewmodels.track

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.badoo.reaktive.single.threadLocal
import com.slavakukhto.lastfm.shared.di.trackDI
import com.slavakukhto.lastfm.shared.domain.models.TrackModel
import com.slavakukhto.lastfm.shared.domain.usecases.GetTrackModelUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import org.kodein.di.instance

abstract class TrackViewModel : BaseViewModel() {

    abstract fun setUpParams(track: String, artist: String)

    abstract fun onArtistClicked(artist: String)

    abstract fun onAlbumClicked(album: String)

    abstract fun onBackClicked()

    abstract fun onPlayClicked()

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

    override fun onArtistClicked(artist: String) {
//        screenNavigator
    }

    override fun onAlbumClicked(album: String) {
//        screenNavigator
    }

    override fun onBackClicked() {
        screenNavigator.popScreen()
    }

    override fun onPlayClicked() {

    }

    override fun loadTrack() {
        if (track.isEmpty() || artist.isEmpty()) {
            dataListener?.onUIDataReceived(TrackUIData.Error(null))
            return
        }
        getTrackModelUseCase.execute(track, artist)
            .doOnAfterSubscribe { dataListener?.onUIDataReceived(TrackUIData.Loading) }
            .threadLocal()
            .subscribe(object : SingleObserver<TrackModel> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(TrackUIData.Error(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: TrackModel) {
                    dataListener?.onUIDataReceived(TrackUIData.Success(value))
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
