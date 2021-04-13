package com.slavakukhto.lastfm.androidApp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.helpers.hideNavigationBar
import com.slavakukhto.lastfm.androidApp.navigator.AndroidGeneralScreenNavigator
import com.slavakukhto.lastfm.shared.di.injectors.provideScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator


class MainActivity : AppCompatActivity() {

    private lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenNavigator = AndroidGeneralScreenNavigator(this)
        provideScreenNavigator(screenNavigator)
        if (savedInstanceState == null) {
            screenNavigator.pushScreen(Screen.SPLASH, null, false)
        }
    }

    override fun onResume() {
        super.onResume()
        hideNavigationBar()
    }

    @SuppressLint("NewApi")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideNavigationBar()
    }

    override fun onBackPressed() {
        screenNavigator.popScreen()
    }
}
