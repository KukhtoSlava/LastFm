package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val generalModule = DI.Module(GENERAL_MODULE) {

    bind<ScreenNavigator>() with singleton {
        navigator
    }
}
