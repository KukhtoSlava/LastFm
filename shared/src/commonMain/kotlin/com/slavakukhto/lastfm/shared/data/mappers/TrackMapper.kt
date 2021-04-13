package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.TrackResponse
import com.slavakukhto.lastfm.shared.domain.models.TrackModel

class TrackMapper {

    fun transform(trackResponse: TrackResponse): TrackModel {
        val tagList = trackResponse.track.toptags?.tag?.let { tags ->
            tags.map { it?.name ?: "" }.filter { it.isNotEmpty() }
        } ?: run {
            emptyList()
        }
        val song = trackResponse.track.name
        val listeners = trackResponse.track.listeners?.toInt() ?: 0
        val scrobbles = trackResponse.track.playcount?.toInt() ?: 0
        val artist = trackResponse.track.artist?.name ?: ""
        val path = trackResponse.track.album?.image?.get(3)?.text
        val imagePath = if (path.isNullOrEmpty()) {
            "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
        } else {
            path
        }
        val album = trackResponse.track.album?.title ?: ""
        val url = trackResponse.track.url
        val wiki = trackResponse.track.wiki?.content ?: ""
        return TrackModel(
            trackName = song,
            listeners = listeners,
            scrobbles = scrobbles,
            artist = artist,
            album = album,
            url = url,
            wiki = wiki,
            imagePath = imagePath,
            tags = tagList,
            youtubeLink = ""
        )
    }
}
