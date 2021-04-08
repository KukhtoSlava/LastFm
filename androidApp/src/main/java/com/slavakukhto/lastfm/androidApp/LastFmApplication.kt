package com.slavakukhto.lastfm.androidApp

import android.app.Application
import com.slavakukhto.lastfm.shared.di.injectors.provideContext

class LastFmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        provideContext(this)
    }
}
