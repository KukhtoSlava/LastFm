package com.slavakukhto.lastfm.androidApp.navigator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.presentation.album.AlbumFragment
import com.slavakukhto.lastfm.androidApp.presentation.artist.ArtistFragment
import com.slavakukhto.lastfm.androidApp.presentation.auth.AuthFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.MainFragment
import com.slavakukhto.lastfm.androidApp.presentation.splash.SplashFragment
import com.slavakukhto.lastfm.androidApp.presentation.track.TrackFragment
import com.slavakukhto.lastfm.shared.presentation.navigation.Screen
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenNavigator
import com.slavakukhto.lastfm.shared.presentation.navigation.ScreenParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.album.AlbumViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.artist.ArtistViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.browser.BrowserScreenParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.track.TrackViewParams
import com.slavakukhto.lastfm.shared.presentation.viewmodels.youtube.YouTubeScreenParams
import java.util.regex.Matcher
import java.util.regex.Pattern

class AndroidGeneralScreenNavigator constructor(
    private val activity: FragmentActivity
) : ScreenNavigator {

    private val handler = Handler(Looper.getMainLooper())

    override fun pushScreen(
        screen: Screen,
        params: ScreenParams?,
        clearBackStack: Boolean,
        withAnimation: Boolean
    ) {
        if (isScreenExternal(screen)) {
            openExternalScreen(screen, params)
        } else {
            openInternalScreens(screen, params, clearBackStack, withAnimation)
        }
    }

    override fun popScreen() {
        with(activity.supportFragmentManager) {
            if (backStackEntryCount > 1) {
                popBackStack()
            } else {
                activity.finish()
                // TODO investigate crash
                handler.postDelayed({ android.os.Process.killProcess(android.os.Process.myPid()) }, 1000)
            }
        }
    }

    private fun isScreenExternal(screen: Screen): Boolean {
        return when (screen) {
            Screen.SPLASH,
            Screen.MAIN,
            Screen.AUTH,
            Screen.TRACK,
            Screen.ARTIST,
            Screen.ALBUM -> false
            Screen.BROWSER, Screen.YOUTUBE -> true
        }
    }

    private fun openInternalScreens(
        screen: Screen,
        params: ScreenParams?,
        clearBackStack: Boolean,
        withAnimation: Boolean
    ) {
        with(activity.supportFragmentManager) {
            val fragment = createScreenFragment(screen, params)
            if (clearBackStack) {
                popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            val fragmentTransaction = beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            if (clearBackStack) {
                fragmentTransaction.addToBackStack(null)
            } else {
                fragmentTransaction.addToBackStack(screen.name)
            }
            if (withAnimation) {
                fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE)
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
            Screen.YOUTUBE -> openYouTubeIfPossible(params)
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
            Screen.TRACK -> TrackFragment.newInstance(screenParams as TrackViewParams)
            Screen.ARTIST -> ArtistFragment.newInstance(screenParams as ArtistViewParams)
            Screen.ALBUM -> AlbumFragment.newInstance(screenParams as AlbumViewParams)
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

    private fun openYouTubeIfPossible(params: ScreenParams?) {
        val url = (params as YouTubeScreenParams).url
        val pattern = "(?<=watch\\?v=|/videos/|embed/)[^#&?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(url)
        val youtubeID = if (matcher.find()) {
            matcher.group()
        } else {
            ""
        }
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID"))
        try {
            activity.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            openBrowserIfPossible(BrowserScreenParams(url))
        }
    }
}
