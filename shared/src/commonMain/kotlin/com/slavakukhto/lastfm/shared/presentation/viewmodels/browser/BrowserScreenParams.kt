package com.slavakukhto.lastfm.shared.presentation.viewmodels.browser

import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenParams

const val FORGOT_PASSWORD_URL = "https://www.last.fm/settings/lostpassword"
const val SIGN_UP_URL = "https://www.last.fm/join?next=/settings"

class BrowserScreenParams(val url: String) : ScreenParams
