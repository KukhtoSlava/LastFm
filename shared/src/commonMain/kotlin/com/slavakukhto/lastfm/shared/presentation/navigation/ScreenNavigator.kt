package com.slavakukhto.lastfm.shared.presentation.navigation

interface ScreenNavigator {

    fun pushScreen(
        screen: Screen,
        params: ScreenParams? = null,
        addToBackStack: Boolean = true,
        withAnimation: Boolean = false
    )

    fun popScreen()
}
