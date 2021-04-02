package com.slavakukhto.lastfm.shared.data.repositoryimpl

import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.data.mappers.UserProfileResponseMapper
import com.slavakukhto.lastfm.shared.data.source.ApiService
import com.slavakukhto.lastfm.shared.data.source.HashApi
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import com.slavakukhto.lastfm.shared.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val hashApi: HashApi,
    private val userProfileResponseMapper: UserProfileResponseMapper
) : AuthRepository {

    override fun getMd5Hash(data: String): Single<String> {
        return singleFromCoroutine { hashApi.getHash(data).digest }
    }

    override fun auth(name: String, password: String, apiSign: String): Single<String> {
        return singleFromCoroutine { apiService.auth(name, password, apiSign) }
    }

    override fun fetchUserInfo(name: String): Single<UserProfile> {
        return singleFromCoroutine {
            userProfileResponseMapper.transformToModel(
                apiService.getUserInfo(
                    name
                )
            )
        }
    }
}
