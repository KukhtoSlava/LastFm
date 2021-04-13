package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTopAlbumsResponse(
    @SerialName("topalbums")
    val topalbums: Topalbums
) {
    @Serializable
    data class Topalbums(
        @SerialName("album")
        val album: List<Album>,
        @SerialName("@attr")
        val attr: Attr?
    ) {
        @Serializable
        data class Album(
            @SerialName("artist")
            val artist: Artist? = null,
            @SerialName("@attr")
            val attr: Attr?,
            @SerialName("image")
            val image: List<Image?>?,
            @SerialName("playcount")
            val playcount: String,
            @SerialName("url")
            val url: String,
            @SerialName("name")
            val name: String,
            @SerialName("mbid")
            val mbid: String
        ) {
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
            data class Attr(
                @SerialName("rank")
                val rank: String
            )

            @Serializable
            data class Image(
                @SerialName("size")
                val size: String,
                @SerialName("#text")
                val text: String
            )
        }

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
    }
}
