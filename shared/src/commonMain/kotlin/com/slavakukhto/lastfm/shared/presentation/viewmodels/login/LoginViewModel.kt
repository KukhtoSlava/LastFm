package com.slavakukhto.lastfm.shared.presentation.viewmodels.login

import com.badoo.reaktive.completable.*
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.slavakukhto.lastfm.shared.di.loginModule
import com.slavakukhto.lastfm.shared.domain.usecases.AuthUserUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.FORGOT_PASSWORD_URL
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.SIGN_UP_URL
import org.kodein.di.DI
import org.kodein.di.instance
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData as UIData

abstract class LoginViewModel : BaseViewModel() {

    abstract fun signInClicked(loginParams: LoginParams)

    abstract fun signUpClicked()

    abstract fun resetPasswordClicked()
}

class LoginViewModelImpl : LoginViewModel() {

    private val di = DI {
        import(loginModule)
    }

    private val authUserUseCase: AuthUserUseCase by di.instance()
    private val screenNavigator: ScreenNavigator by di.instance()

    override fun signInClicked(loginParams: LoginParams) {
        authUserUseCase.execute(loginParams.name, loginParams.password)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnAfterSubscribe {
                dataListener?.onUIDataReceived(LoginUIData.Loading)
            }
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    dataListener?.onUIDataReceived(LoginUIData.Success)
                    screenNavigator.pushScreen(Screen.MAIN)
                }

                override fun onError(error: Throwable) {
                    dataListener?.onUIDataReceived(LoginUIData.ErrorAuth(error.message))
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }
            })
    }

    override fun signUpClicked() {
        screenNavigator.pushScreen(Screen.BROWSER, BrowserScreenParams(SIGN_UP_URL))
    }

    override fun resetPasswordClicked() {
        screenNavigator.pushScreen(Screen.BROWSER, BrowserScreenParams(FORGOT_PASSWORD_URL))
    }
}

// Params
data class LoginParams(val name: String, val password: String)

// UIData
sealed class LoginUIData : UIData {

    object Loading : LoginUIData()
    object Success : LoginUIData()
    class ErrorAuth(val message: String?) : LoginUIData()
}
