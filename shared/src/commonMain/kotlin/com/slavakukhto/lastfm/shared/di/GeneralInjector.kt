package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
lateinit var navigator: ScreenNavigator

fun provideScreenNavigator(screenNavigator: ScreenNavigator): ScreenNavigator {
    navigator = screenNavigator
    return screenNavigator
}
