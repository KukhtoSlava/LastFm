package com.slavakukhto.lastfm.shared.data.repositoryimpl

import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.data.mappers.UserProfileResponseMapper
import com.slavakukhto.lastfm.shared.rxhelpers.singleFromCoroutineUnsafe
import com.slavakukhto.lastfm.shared.data.source.ApiService
import com.slavakukhto.lastfm.shared.data.source.HashApi
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import com.slavakukhto.lastfm.shared.domain.repository.AuthRepository
import com.slavakukhto.lastfm.shared.rxhelpers.uiRxDispatcher

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val hashApi: HashApi,
    private val userProfileResponseMapper: UserProfileResponseMapper
) : AuthRepository {

    override fun getMd5Hash(data: String): Single<String> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            hashApi.getHash(data).digest
        }
    }

    override fun auth(name: String, password: String, apiSign: String): Single<String> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            apiService.auth(name, password, apiSign)
        }
    }

    override fun fetchUserInfo(name: String): Single<UserProfile> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            userProfileResponseMapper.transformToModel(
                apiService.getUserInfo(
                    name
                )
            )
        }
    }
}
