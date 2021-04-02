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
        val mbid: String,
        @SerialName("url")
        val url: String,
        @SerialName("duration")
        val duration: String,
        @SerialName("streamable")
        val streamable: Streamable?,
        @SerialName("listeners")
        val listeners: String?,
        @SerialName("playcount")
        val playcount: String?,
        @SerialName("artist")
        val artist: Artist?,
        @SerialName("album")
        val album: Album?,
        @SerialName("toptags")
        val toptags: Toptags?,
        @SerialName("wiki")
        val wiki: Wiki?
    ) {
        @Serializable
        data class Streamable(
            @SerialName("#text")
            val text: String?,
            @SerialName("fulltrack")
            val fulltrack: String?
        )

        @Serializable
        data class Artist(
            @SerialName("name")
            val name: String?,
            @SerialName("mbid")
            val mbid: String?,
            @SerialName("url")
            val url: String?
        )

        @Serializable
        data class Album(
            @SerialName("artist")
            val artist: String?,
            @SerialName("title")
            val title: String?,
            @SerialName("mbid")
            val mbid: String?,
            @SerialName("url")
            val url: String?,
            @SerialName("image")
            val image: List<Image?>?,
            @SerialName("@attr")
            val attr: Attr?
        ) {
            @Serializable
            data class Image(
                @SerialName("#text")
                val text: String?,
                @SerialName("size")
                val size: String?
            )

            @Serializable
            data class Attr(
                @SerialName("position")
                val position: String?
            )
        }

        @Serializable
        data class Toptags(
            @SerialName("tag")
            val tag: List<Tag?>?
        ) {
            @Serializable
            data class Tag(
                @SerialName("name")
                val name: String?,
                @SerialName("url")
                val url: String?
            )
        }

        @Serializable
        data class Wiki(
            @SerialName("published")
            val published: String?,
            @SerialName("summary")
            val summary: String?,
            @SerialName("content")
            val content: String?
        )
    }
}
