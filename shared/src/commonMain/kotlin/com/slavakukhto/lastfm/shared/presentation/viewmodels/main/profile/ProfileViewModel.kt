package com.slavakukhto.lastfm.shared.presentation.viewmodels.main.profile

import com.badoo.reaktive.completable.CompletableObserver
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.single.SingleObserver
import com.badoo.reaktive.single.doOnAfterSubscribe
import com.slavakukhto.lastfm.shared.di.profileDI
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserProfileUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.LogOutUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData
import org.kodein.di.instance

abstract class ProfileViewModel : BaseViewModel() {

    abstract fun loadProfile()

    abstract fun logOutClicked()
}

class ProfileViewModelImpl : ProfileViewModel() {

    private val getUserProfileUseCase: GetUserProfileUseCase by profileDI.instance()
    private val logOutUseCase: LogOutUseCase by profileDI.instance()
    private val screenNavigator: ScreenNavigator by profileDI.instance()

    override fun subscribe() {
        super.subscribe()
        loadProfile()
    }

    override fun loadProfile() {
        getUserProfileUseCase.execute()
            .doOnAfterSubscribe {
                liveData.value = ProfileUIData.Loading
            }
            .subscribe(object : SingleObserver<UserProfile> {
                override fun onError(error: Throwable) {
                    liveData.value = ProfileUIData.Error(error.message)
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(value: UserProfile) {
                    liveData.value = ProfileUIData.Success(value)
                }
            })
    }

    override fun logOutClicked() {
        logOutUseCase.execute()
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    screenNavigator.pushScreen(
                        Screen.AUTH,
                        null,
                        clearBackStack = true,
                        withAnimation = false
                    )
                }

                override fun onError(error: Throwable) {
                    ProfileUIData.Error(error.message)
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }
            })
    }
}

// UIData
sealed class ProfileUIData : UIData {

    object Loading : ProfileUIData()
    class Success(val profile: UserProfile) : ProfileUIData()
    class Error(val message: String?) : ProfileUIData()
}
