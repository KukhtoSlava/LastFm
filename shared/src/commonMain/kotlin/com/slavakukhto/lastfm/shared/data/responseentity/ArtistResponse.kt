package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistResponse(
    @SerialName("artist")
    val artist: Artist
) {
    @Serializable
    data class Artist(
        @SerialName("name")
        val name: String?,
        @SerialName("mbid")
        val mbid: String? = null,
        @SerialName("url")
        val url: String,
        @SerialName("image")
        val image: List<Image?>?,
        @SerialName("streamable")
        val streamable: String,
        @SerialName("ontour")
        val ontour: String?,
        @SerialName("stats")
        val stats: Stats?,
        @SerialName("similar")
        val similar: Similar?,
        @SerialName("tags")
        val tags: Tags?,
        @SerialName("bio")
        val bio: Bio?
    ) {
        @Serializable
        data class Image(
            @SerialName("#text")
            val text: String,
            @SerialName("size")
            val size: String
        )

        @Serializable
        data class Stats(
            @SerialName("listeners")
            val listeners: String?,
            @SerialName("playcount")
            val playcount: String?
        )

        @Serializable
        data class Similar(
            @SerialName("artist")
            val artist: List<Artist?>?
        ) {
            @Serializable
            data class Artist(
                @SerialName("name")
                val name: String?,
                @SerialName("url")
                val url: String?,
                @SerialName("image")
                val image: List<Image?>?
            ) {
                @Serializable
                data class Image(
                    @SerialName("#text")
                    val text: String?,
                    @SerialName("size")
                    val size: String?
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
        data class Bio(
            @SerialName("links")
            val links: Links?,
            @SerialName("published")
            val published: String?,
            @SerialName("summary")
            val summary: String?,
            @SerialName("content")
            val content: String?
        ) {
            @Serializable
            data class Links(
                @SerialName("link")
                val link: Link?
            ) {
                @Serializable
                data class Link(
                    @SerialName("#text")
                    val text: String?,
                    @SerialName("rel")
                    val rel: String?,
                    @SerialName("href")
                    val href: String?
                )
            }
        }
    }
}
