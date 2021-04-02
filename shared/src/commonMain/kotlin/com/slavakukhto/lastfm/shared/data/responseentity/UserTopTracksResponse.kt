package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTopTracksResponse(
    @SerialName("toptracks")
    val toptracks: Toptracks
) {
    @Serializable
    data class Toptracks(
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
            @SerialName("@attr")
            val attr: Attr?,
            @SerialName("duration")
            val duration: String,
            @SerialName("playcount")
            val playcount: String,
            @SerialName("artist")
            val artist: Artist?,
            @SerialName("image")
            val image: List<Image?>?,
            @SerialName("streamable")
            val streamable: Streamable,
            @SerialName("mbid")
            val mbid: String,
            @SerialName("name")
            val name: String,
            @SerialName("url")
            val url: String
        ) {
            @Serializable
            data class Attr(
                @SerialName("rank")
                val rank: String
            )

            @Serializable
            data class Artist(
                @SerialName("url")
                val url: String,
                @SerialName("name")
                val name: String,
                @SerialName("mbid")
                val mbid: String
            )

            @Serializable
            data class Image(
                @SerialName("size")
                val size: String,
                @SerialName("#text")
                val text: String
            )

            @Serializable
            data class Streamable(
                @SerialName("fulltrack")
                val fulltrack: String,
                @SerialName("#text")
                val text: String
            )
        }
    }
}
