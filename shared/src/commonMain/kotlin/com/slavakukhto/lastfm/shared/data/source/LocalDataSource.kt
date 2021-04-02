package com.slavakukhto.lastfm.shared.data.source

import com.russhwolf.settings.Settings

interface LocalStorage {

    var userName: String

    var timeStampPeriod: String

    var userProfileJson: String

    fun clear()
}

class LocalStorageImpl(private val settings: Settings) : LocalStorage {

    override var userName: String
        get() = settings.getString(PREFS_USER_NAME)
        set(value) = settings.putString(PREFS_USER_NAME, value)

    override var timeStampPeriod: String
        get() = settings.getString(PREFS_TIMESTAMP_PERIOD)
        set(value) = settings.putString(PREFS_TIMESTAMP_PERIOD, value)

    override var userProfileJson: String
        get() = settings.getString(PREFS_USER_PROFILE)
        set(value) = settings.putString(PREFS_USER_PROFILE, value)

    override fun clear() = settings.clear()

    private companion object {

        private const val PREFS_USER_NAME = "PREFS_USER_NAME"
        private const val PREFS_TIMESTAMP_PERIOD = "PREFS_TIMESTAMP_PERIOD"
        private const val PREFS_USER_PROFILE = "PREFS_USER_PROFILE"
    }
}
