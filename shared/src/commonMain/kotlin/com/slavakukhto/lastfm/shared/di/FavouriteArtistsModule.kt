package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreFavouriteArtistsUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserFavouritesArtistsUseCase
import org.kodein.di.*

val favouritesArtistsDI = LazyDI {
    DI {
        extend(mainDI)
        import(favouritesArtistsModule)
    }
}

val favouritesArtistsModule = DI.Module(FAVOURITE_ARTISTS_MODULE) {

    bind() from provider {
        GetUserFavouritesArtistsUseCase(instance(), instance())
    }

    bind() from provider {
        GetMoreFavouriteArtistsUrlUseCase(instance(), instance())
    }
}
