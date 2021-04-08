package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.completable.Completable
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class LogOutUseCase(private val userRepository: UserRepository) {

    fun execute(): Completable {
        return userRepository.clearUserData()
    }
}
