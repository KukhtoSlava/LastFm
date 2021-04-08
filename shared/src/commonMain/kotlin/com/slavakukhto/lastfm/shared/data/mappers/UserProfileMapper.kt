package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.cacheentity.UserProfileCache
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class UserProfileMapper {

    fun transformToObject(json: String): UserProfile {
        val profileCache: UserProfileCache = Json.decodeFromString(json)
        return UserProfile(
            userName = profileCache.userName,
            scrobbles = profileCache.scrobbles,
            country = profileCache.country,
            registrationDate = profileCache.registrationDate,
            profileImagePath = profileCache.profileImagePath
        )
    }

    fun transformToJson(userProfile: UserProfile): String {
        val profile = UserProfileCache(
            userName = userProfile.userName,
            scrobbles = userProfile.scrobbles,
            country = userProfile.country,
            registrationDate = userProfile.registrationDate,
            profileImagePath = userProfile.profileImagePath
        )
        val serializer = serializer<UserProfileCache>()
        return Json.encodeToString(serializer, profile)
    }
}
