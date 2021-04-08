package com.slavakukhto.lastfm.shared.domain.models

data class ScrobblesTrack(
    val track: String,
    val artist: String,
    val imagePath: String,
    val date: String
)
