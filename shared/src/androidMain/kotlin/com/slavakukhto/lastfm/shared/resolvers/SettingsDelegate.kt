package com.slavakukhto.lastfm.shared.resolvers

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings

actual fun provideSettings(context: Any?): Settings {
    return AndroidSettings.Factory(context = context as Context).create()
}
