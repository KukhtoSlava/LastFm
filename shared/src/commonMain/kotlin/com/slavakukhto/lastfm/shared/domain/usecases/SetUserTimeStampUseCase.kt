package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.completable.Completable
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class SetUserTimeStampUseCase(private val userRepository: UserRepository) {

    fun execute(timeStampPeriod: TimeStampPeriod): Completable {
        return userRepository.setUserTimeStamp(timeStampPeriod)
    }
}
