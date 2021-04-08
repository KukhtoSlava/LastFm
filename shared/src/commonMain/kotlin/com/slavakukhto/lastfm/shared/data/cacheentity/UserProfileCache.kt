package com.slavakukhto.lastfm.shared.data.cacheentity

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileCache(
    val userName: String,
    val scrobbles: Long,
    val country: String,
    val registrationDate: Long,
    val profileImagePath: String? = null
)
