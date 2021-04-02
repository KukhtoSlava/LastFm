package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class GetUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(): Single<String> = userRepository.getUserName()
}
