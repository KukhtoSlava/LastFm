package com.slavakukhto.lastfm.shared.domain.models

data class FavouriteAlbum(
    val album: String,
    val scrobbles: Int,
    val imagePath: String
)
