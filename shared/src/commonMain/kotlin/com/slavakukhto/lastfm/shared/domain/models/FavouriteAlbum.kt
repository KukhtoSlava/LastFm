package com.slavakukhto.lastfm.shared.domain.models

data class FavouriteAlbum(
    val artist: String,
    val album: String,
    val scrobbles: Int,
    val imagePath: String
)
