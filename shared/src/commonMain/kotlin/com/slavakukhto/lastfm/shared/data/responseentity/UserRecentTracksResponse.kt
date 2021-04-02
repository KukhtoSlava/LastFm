package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRecentTracksResponse(
    @SerialName("recenttracks")
    val recenttracks: Recenttracks
) {
    @Serializable
    data class Recenttracks(
        @SerialName("@attr")
        val attr: Attr?,
        @SerialName("track")
        val track: List<Track>
    ) {
        @Serializable
        data class Attr(
            @SerialName("page")
            val page: String,
            @SerialName("perPage")
            val perPage: String,
            @SerialName("user")
            val user: String,
            @SerialName("total")
            val total: String,
            @SerialName("totalPages")
            val totalPages: String
        )

        @Serializable
        data class Track(
            @SerialName("artist")
            val artist: Artist,
            @SerialName("@attr")
            val attr: Attr? = null,
            @SerialName("mbid")
            val mbid: String,
            @SerialName("album")
            val album: Album?,
            @SerialName("streamable")
            val streamable: String?,
            @SerialName("url")
            val url: String,
            @SerialName("name")
            val name: String?,
            @SerialName("image")
            val image: List<Image?>?,
            @SerialName("date")
            val date: Date? = null
        ) {
            @Serializable
            data class Artist(
                @SerialName("mbid")
                val mbid: String,
                @SerialName("#text")
                val text: String
            )

            @Serializable
            data class Attr(
                @SerialName("nowplaying")
                val nowplaying: String
            )

            @Serializable
            data class Album(
                @SerialName("mbid")
                val mbid: String,
                @SerialName("#text")
                val text: String
            )

            @Serializable
            data class Image(
                @SerialName("size")
                val size: String,
                @SerialName("#text")
                val text: String
            )

            @Serializable
            data class Date(
                @SerialName("uts")
                val uts: String,
                @SerialName("#text")
                val text: String
            )
        }
    }
}
