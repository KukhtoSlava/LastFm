package com.slavakukhto.lastfm.shared.presentation.viewmodels.album

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.badoo.reaktive.single.threadLocal
import com.slavakukhto.lastfm.shared.di.albumDI
import com.slavakukhto.lastfm.shared.domain.models.AlbumModel
import com.slavakukhto.lastfm.shared.domain.usecases.GetAlbumModelUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackViewParams
import org.kodein.di.instance

abstract class AlbumViewModel : BaseViewModel() {

    abstract fun setUpParams(album: String, artist: String)

    abstract fun onBackClicked()

    abstract fun loadAlbum()

    abstract fun onUrlClicked()

    abstract fun onTrackClicked(artist: String, track: String)

    abstract fun onArtistClicked(artist: String)
}

class AlbumViewModelImpl : AlbumViewModel() {

    private val screenNavigator: ScreenNavigator by albumDI.instance()
    private val getAlbumModelUseCase: GetAlbumModelUseCase by albumDI.instance()
    private var artist: String = ""
    private var album: String = ""

    override fun setUpParams(album: String, artist: String) {
        this.artist = artist
        this.album = album
    }

    override fun subscribe() {
        super.subscribe()
        loadAlbum()
    }

    override fun onBackClicked() {
        screenNavigator.popScreen()
    }

    override fun loadAlbum() {
        if (artist.isEmpty() || album.isEmpty()) {
            dataListener?.onUIDataReceived(AlbumUIData.Error(null))
            return
        }
        getAlbumModelUseCase.execute(artist, album)
            .doOnAfterSubscribe { dataListener?.onUIDataReceived(AlbumUIData.Loading) }
            .threadLocal()
            .subscribe(object : SingleObserver<AlbumModel> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(AlbumUIData.Error(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: AlbumModel) {
                    dataListener?.onUIDataReceived(AlbumUIData.Success(value))
                }
            })
    }

    override fun onUrlClicked() {

    }

    override fun onTrackClicked(artist: String, track: String) {
        val trackViewParams = TrackViewParams(track, artist)
        screenNavigator.pushScreen(Screen.TRACK, trackViewParams, clearBackStack = false, withAnimation = true)
    }

    override fun onArtistClicked(artist: String) {
        val artistViewParams = ArtistViewParams(artist)
        screenNavigator.pushScreen(Screen.ARTIST, artistViewParams, clearBackStack = false, withAnimation = true)
    }
}

// UIData
sealed class AlbumUIData : UIData {

    object Loading : AlbumUIData()
    class Success(val albumModel: AlbumModel) : AlbumUIData()
    class Error(val message: String?) : AlbumUIData()
}
