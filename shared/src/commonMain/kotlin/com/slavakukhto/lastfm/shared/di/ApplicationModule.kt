package com.slavakukhto.lastfm.shared.di

import com.russhwolf.settings.Settings
import com.slavakukhto.lastfm.shared.data.mappers.*
import com.slavakukhto.lastfm.shared.data.repositoryimpl.AuthRepositoryImpl
import com.slavakukhto.lastfm.shared.data.repositoryimpl.MediaRepositoryImpl
import com.slavakukhto.lastfm.shared.data.repositoryimpl.UserRepositoryImpl
import com.slavakukhto.lastfm.shared.data.source.*
import com.slavakukhto.lastfm.shared.di.injectors.appContext
import com.slavakukhto.lastfm.shared.di.injectors.htmlParser
import com.slavakukhto.lastfm.shared.domain.repository.AuthRepository
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository
import com.slavakukhto.lastfm.shared.resolvers.HtmlParser
import com.slavakukhto.lastfm.shared.resolvers.provideSettings
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.*

val applicationDi = LazyDI {
    DI {
        import(applicationModule)
    }
}

val applicationModule = DI.Module(APP_MODULE) {

    bind<Settings>() with singleton {
        provideSettings(appContext)
    }

    bind<HtmlParser>() with singleton {
        htmlParser
    }

    bind<LocalStorage>() with singleton {
        LocalStorageImpl(instance())
    }

    bind() from singleton {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    bind<ApiService>() with singleton {
        LastFmApiService(instance())
    }

    bind<HashApi>() with singleton {
        Md5HashApi(instance())
    }

    bind<AuthRepository>() with singleton {
        AuthRepositoryImpl(instance(), instance(), instance())
    }

    bind<UserRepository>() with singleton {
        UserRepositoryImpl(instance(), instance(), instance())
    }

    bind<MediaRepository>() with singleton {
        MediaRepositoryImpl(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
            instance()
        )
    }

    bind() from singleton {
        UserProfileResponseMapper()
    }

    bind() from singleton {
        TimeStampPeriodMapper()
    }

    bind() from singleton {
        UserProfileMapper()
    }

    bind() from singleton {
        UserScrobblesTracksMapper()
    }

    bind() from singleton {
        UserFavouriteTracksMapper()
    }

    bind() from singleton {
        UserFavouriteAlbumsMapper()
    }

    bind() from singleton {
        UserFavouriteArtistsMapper()
    }

    bind() from singleton {
        TrackMapper()
    }

    bind() from singleton {
        ArtistMapper()
    }

    bind<ParseYouTubeSource>() with singleton {
        ParseYouTubeSourceImpl(instance())
    }
}
