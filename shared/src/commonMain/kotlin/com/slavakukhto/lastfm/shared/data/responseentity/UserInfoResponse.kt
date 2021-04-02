package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("user")
    val user: User
) {

    @Serializable
    data class User(
        @SerialName("playlists")
        val playlists: String,
        @SerialName("playcount")
        val playcount: String,
        @SerialName("gender")
        val gender: String?,
        @SerialName("name")
        val name: String,
        @SerialName("subscriber")
        val subscriber: String,
        @SerialName("url")
        val url: String,
        @SerialName("country")
        val country: String,
        @SerialName("image")
        val image: List<Image?>?,
        @SerialName("registered")
        val registered: Registered,
        @SerialName("type")
        val type: String,
        @SerialName("age")
        val age: String?,
        @SerialName("bootstrap")
        val bootstrap: String,
        @SerialName("realname")
        val realname: String
    ) {

        @Serializable
        data class Image(
            @SerialName("size")
            val size: String,
            @SerialName("#text")
            val text: String
        )

        @Serializable
        data class Registered(
            @SerialName("unixtime")
            val unixtime: String,
            @SerialName("#text")
            val text: Int
        )
    }
}
