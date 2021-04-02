package com.slavakukhto.lastfm.shared.di

import org.kodein.di.DI

val splashModule = DI.Module(SPLASH_MODULE) {

    importOnce(applicationModule)
    importOnce(generalModule)
}
