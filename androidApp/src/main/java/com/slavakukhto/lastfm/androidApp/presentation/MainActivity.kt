package com.slavakukhto.lastfm.androidApp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.navigator.AndroidGeneralScreenNavigator
import com.slavakukhto.lastfm.shared.di.GeneralInjector
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator

class MainActivity : AppCompatActivity() {

    private val screenNavigator: ScreenNavigator = AndroidGeneralScreenNavigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GeneralInjector.provideScreenNavigator(screenNavigator)
        if (savedInstanceState == null) {
            screenNavigator.pushScreen(Screen.SPLASH, null, false)
        }
    }

    override fun onBackPressed() {
        screenNavigator.popScreen()
    }
}
