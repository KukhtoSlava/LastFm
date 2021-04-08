package com.slavakukhto.lastfm.androidApp.presentation.main.albums

import com.slavakukhto.lastfm.shared.domain.models.FavouriteAlbum

interface FavouriteAlbumsListener {

    fun onFavouriteAlbumClicked(favouriteAlbum: FavouriteAlbum)

    fun onFavouriteAlbumsMoreClicked()
}
