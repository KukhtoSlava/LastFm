package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.IsUserAuthorizedUseCase
import org.kodein.di.*

val splashDI = LazyDI {
    DI {
        extend(generalDI)
        import(splashModule)
    }
}

val splashModule = DI.Module(SPLASH_MODULE) {

    bind() from provider {
        IsUserAuthorizedUseCase(instance())
    }
}
