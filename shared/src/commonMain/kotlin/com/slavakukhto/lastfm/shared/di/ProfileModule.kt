package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetUserProfileUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.LogOutUseCase
import org.kodein.di.*

val profileDI = LazyDI {
    DI {
        extend(mainDI)
        import(profileModule)
    }
}

val profileModule = DI.Module(PROFILE_MODULE) {

    bind() from provider {
        GetUserProfileUseCase(instance(), instance())
    }

    bind() from provider {
        LogOutUseCase(instance())
    }
}
