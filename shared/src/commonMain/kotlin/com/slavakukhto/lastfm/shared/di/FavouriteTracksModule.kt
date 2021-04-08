package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.GetMoreFavouriteTracksUrlUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.GetUserFavouritesTracksUseCase
import org.kodein.di.*

val favouritesTracksDI = LazyDI {
    DI {
        extend(mainDI)
        import(favouritesTracksModule)
    }
}

val favouritesTracksModule = DI.Module(FAVOURITE_TRACKS_MODULE) {

    bind() from provider {
        GetUserFavouritesTracksUseCase(instance(), instance())
    }

    bind() from provider {
        GetMoreFavouriteTracksUrlUseCase(instance(), instance())
    }
}
