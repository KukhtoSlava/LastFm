package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.observable.Observable
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class TimestampPeriodChangedUseCase(private val userRepository: UserRepository) {

    fun execute(): Observable<TimeStampPeriod> {
        return userRepository.timeStampPeriodChangeState()
    }
}
