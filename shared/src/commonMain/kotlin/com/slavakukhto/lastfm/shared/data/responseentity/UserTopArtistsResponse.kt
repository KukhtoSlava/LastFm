package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTopArtistsResponse(
    @SerialName("topartists")
    val topartists: Topartists
) {
    @Serializable
    data class Topartists(
        @SerialName("artist")
        val artist: List<Artist>,
        @SerialName("@attr")
        val attr: Attr?
    ) {
        @Serializable
        data class Artist(
            @SerialName("@attr")
            val attr: Attr?,
            @SerialName("mbid")
            val mbid: String,
            @SerialName("url")
            val url: String,
            @SerialName("playcount")
            val playcount: String,
            @SerialName("image")
            val image: List<Image?>?,
            @SerialName("name")
            val name: String,
            @SerialName("streamable")
            val streamable: String
        ) {
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
