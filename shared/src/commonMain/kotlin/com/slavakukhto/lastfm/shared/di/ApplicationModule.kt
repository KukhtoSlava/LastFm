package com.slavakukhto.lastfm.shared.di

import com.russhwolf.settings.Settings
import com.slavakukhto.lastfm.shared.data.mappers.TimeStampPeriodMapper
import com.slavakukhto.lastfm.shared.data.mappers.UserProfileMapper
import com.slavakukhto.lastfm.shared.data.mappers.UserProfileResponseMapper
import com.slavakukhto.lastfm.shared.data.repositoryimpl.AuthRepositoryImpl
import com.slavakukhto.lastfm.shared.data.repositoryimpl.UserRepositoryImpl
import com.slavakukhto.lastfm.shared.data.source.*
import com.slavakukhto.lastfm.shared.domain.repository.AuthRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository
import com.slavakukhto.lastfm.shared.resolvers.provideSettings
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val applicationModule = DI.Module(APP_MODULE) {

    bind<Settings>() with singleton {
        provideSettings(appContext)
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

    bind() from singleton {
        UserProfileResponseMapper()
    }

    bind() from singleton {
        TimeStampPeriodMapper()
    }

    bind() from singleton {
        UserProfileMapper()
    }
}
