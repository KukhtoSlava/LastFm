package com.slavakukhto.lastfm.shared.domain.models

data class TrackModel(
    val trackName: String,
    val listeners: Int,
    val scrobbles: Int,
    val tags: List<String>,
    val artist: String,
    val album: String,
    val url: String,
    val wiki: String,
    val imagePath: String,
    val youtubeLink: String
)
