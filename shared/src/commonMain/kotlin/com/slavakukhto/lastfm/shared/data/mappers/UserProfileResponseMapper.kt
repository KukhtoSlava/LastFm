package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.UserInfoResponse
import com.slavakukhto.lastfm.shared.domain.models.UserProfile

class UserProfileResponseMapper {

    fun transformToModel(userInfoResponse: UserInfoResponse): UserProfile {
        return UserProfile(
            userName = userInfoResponse.user.name,
            scrobbles = userInfoResponse.user.playcount.toLong(),
            country = userInfoResponse.user.country,
            registrationDate = userInfoResponse.user.registered.text.toLong() * 1000,
            profileImagePath = userInfoResponse.user.image?.get(3)?.text
        )
    }
}
