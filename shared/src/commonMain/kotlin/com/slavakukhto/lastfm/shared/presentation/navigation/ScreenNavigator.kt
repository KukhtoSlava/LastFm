package com.slavakukhto.lastfm.shared.presentation.navigation

interface ScreenNavigator {

    fun pushScreen(screen: Screen, params: ScreenParams? = null, addToBackStack: Boolean = true)

    fun popScreen()
}
