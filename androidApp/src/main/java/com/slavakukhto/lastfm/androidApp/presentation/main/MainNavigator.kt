package com.slavakukhto.lastfm.androidApp.presentation.main

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.slavakukhto.lastfm.androidApp.R
import com.slavakukhto.lastfm.androidApp.presentation.main.albums.FavouriteAlbumsFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.artists.FavouriteArtistsFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.profile.ProfileFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.scrobbles.ScrobblesFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.tracks.FavouriteTracksFragment

class MainNavigator(
    private val fragmentManager: FragmentManager,
    private val title: TextView,
    private val navigationView: BottomNavigationView,
) {

    private val favouriteTracks = FavouriteTracksFragment()
    private val favouriteAlbums = FavouriteAlbumsFragment()
    private val favouriteArtists = FavouriteArtistsFragment()
    private val scrobbles = ScrobblesFragment()
    private val profile = ProfileFragment()
    private var active: Fragment = scrobbles

    init {
        initialize()
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.scrobbles -> {
                    fragmentManager.beginTransaction().hide(active).show(scrobbles).commit()
                    title.text = active.getText(R.string.scrobbles)
                    active = scrobbles
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.artists -> {
                    fragmentManager.beginTransaction().hide(active).show(favouriteArtists).commit()
                    title.text = active.getText(R.string.artists)
                    active = favouriteArtists
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.albums -> {
                    fragmentManager.beginTransaction().hide(active).show(favouriteAlbums).commit()
                    title.text = active.getText(R.string.albums)
                    active = favouriteAlbums
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tracks -> {
                    fragmentManager.beginTransaction().hide(active).show(favouriteTracks).commit()
                    title.text = active.getText(R.string.tracks)
                    active = favouriteTracks
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    fragmentManager.beginTransaction().hide(active).show(profile).commit()
                    title.text = active.getText(R.string.profile)
                    active = profile
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initialize() {
        fragmentManager.beginTransaction().add(R.id.container, favouriteTracks, FavouriteTracksFragment::javaClass.name)
            .hide(favouriteTracks).commit()
        fragmentManager.beginTransaction().add(R.id.container, favouriteAlbums, FavouriteAlbumsFragment::javaClass.name)
            .hide(favouriteAlbums).commit()
        fragmentManager.beginTransaction()
            .add(R.id.container, favouriteArtists, FavouriteArtistsFragment::javaClass.name).hide(favouriteArtists)
            .commit()
        fragmentManager.beginTransaction().add(R.id.container, profile, ProfileFragment::javaClass.name).hide(profile)
            .commit()
        fragmentManager.beginTransaction().add(R.id.container, scrobbles, ScrobblesFragment::javaClass.name)
            .runOnCommit {
                title.text = scrobbles.getText(R.string.scrobbles)
                navigationView.menu.findItem(R.id.scrobbles).isChecked = true
            }.commit()
    }
}
