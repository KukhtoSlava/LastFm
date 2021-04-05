package com.slavakukhto.lastfm.shared.presentation.viewmodels.splash

import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.single.subscribeOn
import com.slavakukhto.lastfm.shared.di.splashModule
import com.slavakukhto.lastfm.shared.domain.usecases.IsUserAuthorizedUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import org.kodein.di.DI
import org.kodein.di.instance

abstract class SplashViewModel : BaseViewModel()

class SplashViewModelImpl : SplashViewModel() {

    private val di = DI {
        import(splashModule)
    }

    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase by di.instance()
    private val screenNavigator: ScreenNavigator by di.instance()

    override fun subscribe() {
        isUserAuthorizedUseCase.execute()
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                onSuccess = { isUserAuth ->
                    if (isUserAuth) {
                        screenNavigator.pushScreen(Screen.MAIN)
                    } else {
                        screenNavigator.pushScreen(Screen.AUTH)
                    }
                },
                onError = {
                    screenNavigator.pushScreen(Screen.AUTH)
                }
            )
    }
}
