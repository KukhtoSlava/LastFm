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
        val image: List<Image?>?,
        @SerialName("listeners")
        val listeners: String?,
        @SerialName("playcount")
        val playcount: String?,
        @SerialName("tracks")
        val tracks: Tracks?,
        @SerialName("tags")
        val tags: Tags?,
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
            val track: List<Track>?
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
                val attr: Attr?,
                @SerialName("streamable")
                val streamable: Streamable,
                @SerialName("artist")
                val artist: Artist
            ) {
                @Serializable
                data class Attr(
                    @SerialName("rank")
                    val rank: String?
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
            val tag: List<Tag>?
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
