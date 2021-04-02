package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.single.flatMap
import com.badoo.reaktive.single.flatMapCompletable
import com.badoo.reaktive.single.singleOf
import com.slavakukhto.lastfm.shared.API_KEY
import com.slavakukhto.lastfm.shared.SECRET
import com.slavakukhto.lastfm.shared.domain.repository.AuthRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class AuthUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    fun execute(name: String, password: String): Completable {
        return singleOf(encodeParams(name, password))
            .flatMap { key -> authRepository.getMd5Hash(key) }
            .flatMap { sig -> authRepository.auth(name, password, sig) }
            .flatMapCompletable { userName -> userRepository.setUserName(userName) }
    }

    private fun encodeParams(name: String, password: String): String {
        return "$KEY_API_KEY$API_KEY$KEY_METHOD${password}$KEY_USER_NAME$name$SECRET"
    }

    private companion object {

        private const val KEY_API_KEY = "api_key"
        private const val KEY_METHOD = "methodauth.getMobileSessionpassword"
        private const val KEY_USER_NAME = "username"
    }
}
