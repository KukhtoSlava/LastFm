package com.slavakukhto.lastfm.shared.domain.repository

import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.domain.models.UserProfile

interface AuthRepository {

    fun getMd5Hash(data: String): Single<String>

    fun auth(name: String, password: String, apiSign: String): Single<String>

    fun fetchUserInfo(name: String): Single<UserProfile>
}
