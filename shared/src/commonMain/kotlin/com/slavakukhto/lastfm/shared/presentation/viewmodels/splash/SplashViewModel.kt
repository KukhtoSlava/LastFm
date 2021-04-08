package com.slavakukhto.lastfm.shared.presentation.viewmodels.splash

import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.single.subscribeOn
import com.badoo.reaktive.single.threadLocal
import com.slavakukhto.lastfm.shared.di.splashDI
import com.slavakukhto.lastfm.shared.domain.usecases.IsUserAuthorizedUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import org.kodein.di.instance

abstract class SplashViewModel : BaseViewModel()

class SplashViewModelImpl : SplashViewModel() {

    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase by splashDI.instance()
    private val screenNavigator: ScreenNavigator by splashDI.instance()

    override fun subscribe() {
        isUserAuthorizedUseCase.execute()
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .threadLocal()
            .subscribe(
                onSuccess = { isUserAuth ->
                    if (isUserAuth) {
                        screenNavigator.pushScreen(
                            Screen.MAIN,
                            null,
                            clearBackStack = true,
                            withAnimation = false
                        )
                    } else {
                        screenNavigator.pushScreen(
                            Screen.AUTH,
                            null,
                            clearBackStack = true,
                            withAnimation = false
                        )
                    }
                },
                onError = {
                    screenNavigator.pushScreen(
                        Screen.AUTH,
                        null,
                        clearBackStack = true,
                        withAnimation = false
                    )
                }
            )
    }
}
