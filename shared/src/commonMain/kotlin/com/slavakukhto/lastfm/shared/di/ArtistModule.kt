package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetArtistModelUseCase
import org.kodein.di.*

val artistDI = LazyDI {
    DI {
        extend(generalDI)
        import(artistModule)
    }
}

val artistModule = DI.Module(ARTIST_MODULE) {

    bind() from provider {
        GetArtistModelUseCase(instance(), instance())
    }
}
