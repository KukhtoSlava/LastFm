package com.slavakukhto.lastfm.androidApp.presentation.main.tracks

import com.slavakukhto.lastfm.shared.domain.models.FavouriteTrack

interface FavouriteTracksListener {

    fun onFavouriteTrackClicked(favouriteTrack: FavouriteTrack)

    fun onFavouriteTracksMoreClicked()
}
