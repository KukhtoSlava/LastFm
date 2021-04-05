package com.slavakukhto.lastfm.shared.resolvers

import com.russhwolf.settings.Settings
import com.russhwolf.settings.AppleSettings

actual fun provideSettings(context: Any?): Settings {
    return AppleSettings.Factory().create()
}
