package com.slavakukhto.lastfm.androidApp.presentation.main.artists

import com.slavakukhto.lastfm.shared.domain.models.FavouriteArtist

interface FavouriteArtistsListener {

    fun onFavouriteArtistClicked(favouriteArtist: FavouriteArtist)

    fun onFavouriteArtistsMoreClicked()
}
