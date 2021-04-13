package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.AlbumResponse
import com.slavakukhto.lastfm.shared.domain.models.AlbumModel

class AlbumMapper {

    fun transform(albumResponse: AlbumResponse): AlbumModel {
        val album = albumResponse.album.name
        val artist = albumResponse.album.artist
        val tagList = albumResponse.album.tags?.tag?.let { tags ->
            tags.map { it.name ?: "" }.filter { it.isNotEmpty() }
        } ?: run {
            emptyList()
        }
        val trackList =
            albumResponse.album.tracks?.track?.let { tracks ->
                tracks.map { it.name }
            } ?: run {
                emptyList()
            }
        val listeners = albumResponse.album.listeners?.toInt() ?: 0
        val scrobbles = albumResponse.album.playcount?.toInt() ?: 0
        val path = albumResponse.album.image?.get(3)?.text
        val imagePath = if (path.isNullOrEmpty()) {
            "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
        } else {
            path
        }
        val url = albumResponse.album.url
        val wiki = albumResponse.album.wiki?.content ?: ""
        return AlbumModel(
            album = album,
            listeners = listeners,
            scrobbles = scrobbles,
            artist = artist,
            url = url,
            wiki = wiki,
            imagePath = imagePath,
            tags = tagList,
            tracks = trackList
        )
    }
}
