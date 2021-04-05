package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.IsUserAuthorizedUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val splashModule = DI.Module(SPLASH_MODULE) {

    importOnce(applicationModule)
    importOnce(generalModule)

    bind() from provider {
        IsUserAuthorizedUseCase(instance())
    }
}
