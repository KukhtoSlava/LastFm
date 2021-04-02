package com.slavakukhto.lastfm.shared.data.responseentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MD5Response(
    @SerialName("Digest")
    val digest: String,
    @SerialName("DigestEnc")
    val digestEnc: String,
    @SerialName("Type")
    val type: String,
    @SerialName("Key")
    val key: String
)
