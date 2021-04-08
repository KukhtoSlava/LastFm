package com.slavakukhto.lastfm.shared.domain.models

data class FavouriteArtist(
    val artist: String,
    val scrobbles: Int,
    val imagePath: String
)
