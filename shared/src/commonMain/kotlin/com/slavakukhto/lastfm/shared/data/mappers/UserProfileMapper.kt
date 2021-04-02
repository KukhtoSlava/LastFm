package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class UserProfileMapper {

    fun transformToObject(json: String): UserProfile {
        return Json.decodeFromString(json)
    }

    fun transformToJson(userProfile: UserProfile): String {
        val serializer = serializer<UserProfile>()
        return Json.encodeToString(serializer, userProfile)
    }
}
