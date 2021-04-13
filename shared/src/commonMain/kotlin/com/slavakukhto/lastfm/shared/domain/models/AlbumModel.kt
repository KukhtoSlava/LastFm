package com.slavakukhto.lastfm.shared.domain.models

data class AlbumModel(
    val artist: String,
    val album: String,
    val listeners: Int,
    val scrobbles: Int,
    val tracks: List<String>,
    val tags: List<String>,
    val url: String,
    val wiki: String,
    val imagePath: String
)
