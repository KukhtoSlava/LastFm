package com.slavakukhto.lastfm.shared.domain.models

data class FavouriteTrack(
    val track: String,
    val artist: String,
    val scrobbles: Int,
    val imagePath: String
)
