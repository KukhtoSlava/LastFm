package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.map
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class IsUserAuthorizedUseCase(private val userRepository: UserRepository) {

    fun execute(): Single<Boolean> {
        return userRepository.getUserName()
            .map { userName -> userName.isNotEmpty() }
    }
}
