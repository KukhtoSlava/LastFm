package com.slavakukhto.lastfm.shared.presentation.viewmodels.artist

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.badoo.reaktive.single.threadLocal
import com.slavakukhto.lastfm.shared.di.artistDI
import com.slavakukhto.lastfm.shared.domain.models.ArtistModel
import com.slavakukhto.lastfm.shared.domain.usecases.GetArtistModelUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import org.kodein.di.instance

abstract class ArtistViewModel : BaseViewModel() {

    abstract fun setUpParams(artist: String)

    abstract fun onBackClicked()

    abstract fun loadArtist()

    abstract fun onUrlClicked()
}

class ArtistViewModelImpl : ArtistViewModel() {

    private val screenNavigator: ScreenNavigator by artistDI.instance()
    private val getArtistModelUseCase: GetArtistModelUseCase by artistDI.instance()
    private var artist: String = ""

    override fun setUpParams(artist: String) {
        this.artist = artist
    }

    override fun subscribe() {
        super.subscribe()
        loadArtist()
    }

    override fun onBackClicked() {
        screenNavigator.popScreen()
    }

    override fun onUrlClicked() {

    }

    override fun loadArtist() {
        if (artist.isEmpty()) {
            dataListener?.onUIDataReceived(ArtistUIData.Error(null))
            return
        }
        getArtistModelUseCase.execute(artist)
            .doOnAfterSubscribe { dataListener?.onUIDataReceived(ArtistUIData.Loading) }
            .threadLocal()
            .subscribe(object : SingleObserver<ArtistModel> {
                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(ArtistUIData.Error(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: ArtistModel) {
                    dataListener?.onUIDataReceived(ArtistUIData.Success(value))
                }
            })
    }
}

// UIData
sealed class ArtistUIData : UIData {

    object Loading : ArtistUIData()
    class Success(val artist: ArtistModel) : ArtistUIData()
    class Error(val message: String?) : ArtistUIData()
}
