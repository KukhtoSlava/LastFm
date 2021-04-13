package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumResponse(
    @SerialName("album")
    val album: Album
) {
    @Serializable
    data class Album(
        @SerialName("name")
        val name: String,
        @SerialName("artist")
        val artist: String,
        @SerialName("mbid")
        val mbid: String? = null,
        @SerialName("url")
        val url: String,
        @SerialName("image")
        val image: List<Image?>? = null,
        @SerialName("listeners")
        val listeners: String? = null,
        @SerialName("playcount")
        val playcount: String? = null,
        @SerialName("tracks")
        val tracks: Tracks? = null,
        @SerialName("tags")
        val tags: Tags? = null,
        @SerialName("wiki")
        val wiki: Wiki? = null
    ) {
        @Serializable
        data class Image(
            @SerialName("#text")
            val text: String,
            @SerialName("size")
            val size: String
        )

        @Serializable
        data class Tracks(
            @SerialName("track")
            val track: List<Track>? = null
        ) {
            @Serializable
            data class Track(
                @SerialName("name")
                val name: String,
                @SerialName("url")
                val url: String,
                @SerialName("duration")
                val duration: String,
                @SerialName("@attr")
                val attr: Attr? = null,
                @SerialName("streamable")
                val streamable: Streamable,
                @SerialName("artist")
                val artist: Artist
            ) {
                @Serializable
                data class Attr(
                    @SerialName("rank")
                    val rank: String? = null
                )

                @Serializable
                data class Streamable(
                    @SerialName("#text")
                    val text: String,
                    @SerialName("fulltrack")
                    val fulltrack: String
                )

                @Serializable
                data class Artist(
                    @SerialName("name")
                    val name: String,
                    @SerialName("mbid")
                    val mbid: String? = null,
                    @SerialName("url")
                    val url: String
                )
            }
        }

        @Serializable
        data class Tags(
            @SerialName("tag")
            val tag: List<Tag>? = null
        ) {
            @Serializable
            data class Tag(
                @SerialName("name")
                val name: String? = null,
                @SerialName("url")
                val url: String? = null
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
