package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.AuthUserUseCase
import org.kodein.di.*

val loginDI = LazyDI {
    DI {
        extend(generalDI)
        import(loginModule)
    }
}

val loginModule = DI.Module(SPLASH_MODULE) {

    bind() from provider {
        AuthUserUseCase(instance(), instance())
    }
}
