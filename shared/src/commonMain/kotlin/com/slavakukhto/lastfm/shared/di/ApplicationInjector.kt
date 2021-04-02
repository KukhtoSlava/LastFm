package com.slavakukhto.lastfm.shared.di

import com.russhwolf.settings.Settings
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ApplicationInjector {

    lateinit var settings: Settings

    fun provideSettings(settings: Settings): Settings {
        this.settings = settings
        return settings
    }
}
