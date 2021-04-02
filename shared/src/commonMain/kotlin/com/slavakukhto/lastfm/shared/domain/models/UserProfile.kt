package com.slavakukhto.lastfm.shared.domain.models

data class UserProfile(
    val userName: String,
    val scrobbles: Long,
    val country: String,
    val registrationDate: Long,
    val profileImagePath: String? = null
)
