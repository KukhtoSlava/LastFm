package com.slavakukhto.lastfm.androidApp

import android.app.Application
import com.slavakukhto.lastfm.androidApp.helpers.AndroidHtmlParser
import com.slavakukhto.lastfm.shared.di.injectors.provideContext
import com.slavakukhto.lastfm.shared.di.injectors.provideHtmlParser

class LastFmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val htmlParser = AndroidHtmlParser()
        provideContext(this)
        provideHtmlParser(htmlParser)
    }
}
