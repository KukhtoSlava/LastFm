package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreScrobblesUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserScrobblesTracksUseCase
import org.kodein.di.*

val scrobblesDI = LazyDI {
    DI {
        extend(mainDI)
        import(scrobblesModule)
    }
}

val scrobblesModule = DI.Module(SCROBBLES_MODULE) {

    bind() from provider {
        GetUserScrobblesTracksUseCase(instance(), instance())
    }

    bind() from provider {
        GetMoreScrobblesUrlUseCase(instance(), instance())
    }
}
