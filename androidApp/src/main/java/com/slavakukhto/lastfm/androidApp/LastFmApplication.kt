package com.slavakukhto.lastfm.androidApp

import android.app.Application
import com.slavakukhto.lastfm.shared.di.ApplicationInjector

class LastFmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationInjector.provideContext(this)
    }
}
