package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object GeneralInjector {

    lateinit var screenNavigator: ScreenNavigator

    fun provideScreenNavigator(screenNavigator: ScreenNavigator): ScreenNavigator {
        this.screenNavigator = screenNavigator
        return screenNavigator
    }
}
