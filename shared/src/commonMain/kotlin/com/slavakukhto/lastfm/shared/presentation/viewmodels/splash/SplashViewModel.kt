package com.slavakukhto.lastfm.shared.presentation.viewmodels.splash

import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.single.subscribeOn
import com.slavakukhto.lastfm.shared.domain.usecases.IsUserAuthorizedUseCase
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.viewmodels.BaseViewModel
import com.slavakukhto.lastfm.shared.presentation.viewmodels.UIData

abstract class SplashViewModel : BaseViewModel()

class SplashViewModelImpl(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val screenNavigator: ScreenNavigator
) : SplashViewModel() {

    override fun subscribe(data: ((UIData) -> Unit?)?) {
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
                onError = { screenNavigator.pushScreen(Screen.AUTH) }
            )
    }
}
