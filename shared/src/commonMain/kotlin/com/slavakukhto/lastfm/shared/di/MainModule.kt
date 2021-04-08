package com.slavakukhto.lastfm.shared.di

import com.slavakukhto.lastfm.shared.domain.usecases.SetUserTimeStampUseCase
import com.slavakukhto.lastfm.shared.domain.usecases.TimestampPeriodChangedUseCase
import org.kodein.di.*

val mainDI = LazyDI {
    DI {
        extend(generalDI)
        import(mainModule)
    }
}

val mainModule = DI.Module(MAIN_MODULE) {

    bind() from provider {
        TimestampPeriodChangedUseCase(instance())
    }

    bind() from provider {
        SetUserTimeStampUseCase(instance())
    }
}
