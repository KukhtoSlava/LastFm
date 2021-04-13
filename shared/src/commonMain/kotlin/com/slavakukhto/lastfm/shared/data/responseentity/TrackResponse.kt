package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackResponse(
    @SerialName("track")
    val track: Track
) {
    @Serializable
    data class Track(
        @SerialName("name")
        val name: String,
        @SerialName("mbid")
        val mbid: String? = null,
        @SerialName("url")
        val url: String,
        @SerialName("duration")
        val duration: String,
        @SerialName("streamable")
        val streamable: Streamable? = null,
        @SerialName("listeners")
        val listeners: String? = null,
        @SerialName("playcount")
        val playcount: String? = null,
        @SerialName("artist")
        val artist: Artist? = null,
        @SerialName("album")
        val album: Album? = null,
        @SerialName("toptags")
        val toptags: Toptags? = null,
        @SerialName("wiki")
        val wiki: Wiki? = null
    ) {
        @Serializable
        data class Streamable(
            @SerialName("#text")
            val text: String? = null,
            @SerialName("fulltrack")
            val fulltrack: String? = null
        )

        @Serializable
        data class Artist(
            @SerialName("name")
            val name: String? = null,
            @SerialName("mbid")
            val mbid: String? = null,
            @SerialName("url")
            val url: String? = null
        )

        @Serializable
        data class Album(
            @SerialName("artist")
            val artist: String? = null,
            @SerialName("title")
            val title: String? = null,
            @SerialName("mbid")
            val mbid: String? = null,
            @SerialName("url")
            val url: String? = null,
            @SerialName("image")
            val image: List<Image?>? = null,
            @SerialName("@attr")
            val attr: Attr? = null
        ) {
            @Serializable
            data class Image(
                @SerialName("#text")
                val text: String? = null,
                @SerialName("size")
                val size: String?
            )

            @Serializable
            data class Attr(
                @SerialName("position")
                val position: String? = null
            )
        }

        @Serializable
        data class Toptags(
            @SerialName("tag")
            val tag: List<Tag?>? = null
        ) {
            @Serializable
            data class Tag(
                @SerialName("name")
                val name: String,
                @SerialName("url")
                val url: String?
            )
        }

        @Serializable
        data class Wiki(
            @SerialName("published")
            val published: String? = null,
            @SerialName("summary")
            val summary: String? = null,
            @SerialName("content")
            val content: String? = null
        )
    }
}
