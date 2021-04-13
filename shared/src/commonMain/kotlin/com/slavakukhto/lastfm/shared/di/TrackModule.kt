package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetTrackModelUseCase
import org.kodein.di.*

val trackDI = LazyDI {
    DI {
        extend(generalDI)
        import(trackModule)
    }
}

val trackModule = DI.Module(TRACK_MODULE) {

    bind() from provider {
        GetTrackModelUseCase(instance(), instance())
    }
}
