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
        val name: String? = null,
        @SerialName("mbid")
        val mbid: String? = null,
        @SerialName("url")
        val url: String,
        @SerialName("image")
        val image: List<Image?>? = null,
        @SerialName("streamable")
        val streamable: String,
        @SerialName("ontour")
        val ontour: String? = null,
        @SerialName("stats")
        val stats: Stats? = null,
        @SerialName("similar")
        val similar: Similar? = null,
        @SerialName("tags")
        val tags: Tags? = null,
        @SerialName("bio")
        val bio: Bio? = null
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
            val listeners: String? = null,
            @SerialName("playcount")
            val playcount: String? = null
        )

        @Serializable
        data class Similar(
            @SerialName("artist")
            val artist: List<Artist?>? = null
        ) {
            @Serializable
            data class Artist(
                @SerialName("name")
                val name: String? = null,
                @SerialName("url")
                val url: String? = null,
                @SerialName("image")
                val image: List<Image?>? = null
            ) {
                @Serializable
                data class Image(
                    @SerialName("#text")
                    val text: String? = null,
                    @SerialName("size")
                    val size: String? = null
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
        data class Bio(
            @SerialName("links")
            val links: Links? = null,
            @SerialName("published")
            val published: String? = null,
            @SerialName("summary")
            val summary: String? = null,
            @SerialName("content")
            val content: String? = null
        ) {
            @Serializable
            data class Links(
                @SerialName("link")
                val link: Link? = null
            ) {
                @Serializable
                data class Link(
                    @SerialName("#text")
                    val text: String? = null,
                    @SerialName("rel")
                    val rel: String? = null,
                    @SerialName("href")
                    val href: String? = null
                )
            }
        }
    }
}
