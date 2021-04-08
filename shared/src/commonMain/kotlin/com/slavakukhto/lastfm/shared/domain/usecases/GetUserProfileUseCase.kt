package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.completable.andThen
import com.badoo.reaktive.completable.onErrorComplete
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.badoo.reaktive.single.flatMapCompletable
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import com.slavakukhto.lastfm.shared.domain.repository.AuthRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class GetUserProfileUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {

    fun execute(): Single<UserProfile> {
        return userRepository.getUserName()
            .flatMap { userName ->
                authRepository.fetchUserInfo(userName)
                    .flatMapCompletable { userProfile ->
                        userRepository.updateUserProfile(userProfile)
                    }
                    .onErrorComplete()
                    .andThen(userRepository.getUserProfile())
            }
    }
}
