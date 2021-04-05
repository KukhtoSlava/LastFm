package com.slavakukhto.lastfm.androidApp.navigator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.presentation.auth.AuthFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.MainFragment
import com.slavakukhto.lastfm.androidApp.presentation.splash.SplashFragment
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams

class AndroidGeneralScreenNavigator constructor(private val activity: FragmentActivity) : ScreenNavigator {

    override fun pushScreen(screen: Screen, params: ScreenParams?, addToBackStack: Boolean) {
        if (isScreenExternal(screen)) {
            openExternalScreen(screen, params)
        } else {
            openInternalScreens(screen, params, addToBackStack)
        }
    }

    override fun popScreen() {
        with(activity.supportFragmentManager) {
            if (backStackEntryCount > 1) {
                popBackStack()
            } else {
                activity.finish()
            }
        }
    }

    private fun isScreenExternal(screen: Screen): Boolean {
        return when (screen) {
            Screen.SPLASH,
            Screen.MAIN,
            Screen.AUTH -> false
            Screen.BROWSER -> true
        }
    }

    private fun openInternalScreens(
        screen: Screen,
        params: ScreenParams?,
        addToBackStack: Boolean
    ) {
        with(activity.supportFragmentManager) {
            val fragment = createScreenFragment(screen, params)
            val fragmentTransaction = beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(screen.name)
            }
            fragmentTransaction.commit()
        }
    }

    private fun openExternalScreen(
        screen: Screen,
        params: ScreenParams?
    ) {
        when (screen) {
            Screen.BROWSER -> openBrowserIfPossible(params)
            else -> throw IllegalArgumentException("Unknown external screen type: $screen")
        }
    }

    private fun createScreenFragment(
        screenType: Screen,
        screenParams: ScreenParams?
    ): Fragment {
        return when (screenType) {
            Screen.SPLASH -> SplashFragment()
            Screen.MAIN -> MainFragment()
            Screen.AUTH -> AuthFragment()
            else -> throw IllegalArgumentException(
                "This $screenType screen doesn't seem to be internal screen!"
            )
        }
    }

    private fun openBrowserIfPossible(params: ScreenParams?) {
        try {
            val screenParams = params as BrowserScreenParams
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(screenParams.url))
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}
