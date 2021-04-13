package com.slavakukhto.lastfm.shared.domain.models

data class ArtistModel(
    val artist: String,
    val listeners: Int,
    val scrobbles: Int,
    val tags: List<String>,
    val url: String,
    val wiki: String,
    val imagePath: String
)
