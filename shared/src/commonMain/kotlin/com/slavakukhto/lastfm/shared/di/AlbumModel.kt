package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetAlbumModelUseCase
import org.kodein.di.*

val albumDI = LazyDI {
    DI {
        extend(generalDI)
        import(albumModule)
    }
}

val albumModule = DI.Module(ALBUM_MODULE) {

    bind() from provider {
        GetAlbumModelUseCase(instance())
    }
}
