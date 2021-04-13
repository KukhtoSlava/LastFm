package com.slavakukhto.lastfm.androidApp.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.slavakukhto.lastfm.androidApp.presentation.main.albums.FavouriteAlbumsFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.artists.FavouriteArtistsFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.profile.ProfileFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.scrobbles.ScrobblesFragment
import com.slavakukhto.lastfm.androidApp.presentation.main.tracks.FavouriteTracksFragment

class MainStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {

        const val MAX_ITEMS: Int = 5
        const val SCROBBLES_ITEM = 0
        const val FAVOURITE_ARTISTS_ITEM = 1
        const val FAVOURITE_ALBUMS_ITEM = 2
        const val FAVOURITE_TRACKS_ITEM = 3
        const val PROFILE_ITEM = 4
    }

    override fun getItemCount() = MAX_ITEMS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SCROBBLES_ITEM -> ScrobblesFragment()
            FAVOURITE_ARTISTS_ITEM -> FavouriteArtistsFragment()
            FAVOURITE_ALBUMS_ITEM -> FavouriteAlbumsFragment()
            FAVOURITE_TRACKS_ITEM -> FavouriteTracksFragment()
            PROFILE_ITEM -> ProfileFragment()
            else -> throw IllegalArgumentException("Not valid position")
        }
    }
}
