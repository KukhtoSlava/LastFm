package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreFavouriteAlbumsUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserFavouritesAlbumsUseCase
import org.kodein.di.*

val favouritesAlbumsDI = LazyDI {
    DI {
        extend(mainDI)
        import(favouritesAlbumsModule)
    }
}

val favouritesAlbumsModule = DI.Module(FAVOURITE_ALBUMS_MODULE) {

    bind() from provider {
        GetUserFavouritesAlbumsUseCase(instance(), instance())
    }

    bind() from provider {
        GetMoreFavouriteAlbumsUrlUseCase(instance(), instance())
    }
}
