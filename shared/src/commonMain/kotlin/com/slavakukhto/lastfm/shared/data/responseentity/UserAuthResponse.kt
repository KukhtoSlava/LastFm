package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthResponse(
    @SerialName("session")
    val session: Session
) {
    @Serializable
    data class Session(
        @SerialName("key")
        val key: String,
        @SerialName("name")
        val name: String,
        @SerialName("subscriber")
        val subscriber: Int
    )
}