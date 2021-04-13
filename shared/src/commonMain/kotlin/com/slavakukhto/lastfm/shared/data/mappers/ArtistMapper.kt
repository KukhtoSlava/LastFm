package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.ArtistResponse
import com.slavakukhto.lastfm.shared.domain.models.ArtistModel

class ArtistMapper {

    fun transform(artistResponse: ArtistResponse): ArtistModel {
        val tagList = artistResponse.artist.tags?.tag?.let { tags ->
            tags.map { it.name ?: "" }.filter { it.isNotEmpty() }
        } ?: run {
            emptyList()
        }
        val listeners = artistResponse.artist.stats?.listeners?.toInt() ?: 0
        val scrobbles = artistResponse.artist.stats?.playcount?.toInt() ?: 0
        val artist = artistResponse.artist.name ?: ""
        val imagePath = ""
        val url = artistResponse.artist.url
        val wiki = artistResponse.artist.bio?.content ?: ""
        return ArtistModel(
            listeners = listeners,
            scrobbles = scrobbles,
            artist = artist,
            url = url,
            wiki = wiki,
            imagePath = imagePath,
            tags = tagList
        )
    }
}
