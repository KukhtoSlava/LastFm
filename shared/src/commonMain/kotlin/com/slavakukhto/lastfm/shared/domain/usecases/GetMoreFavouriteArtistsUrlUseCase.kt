package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.badoo.reaktive.single.map
import com.slavakukhto.lastfm.shared.data.mappers.TimeStampPeriodMapper
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class GetMoreFavouriteArtistsUrlUseCase(
    private val userRepository: UserRepository,
    private val timeStampPeriodMapper: TimeStampPeriodMapper
) {

    fun execute(): Single<String> {
        return userRepository.getUserName()
            .flatMap { userName ->
                userRepository.getUserTimeStamp()
                    .map { period ->
                        generateUrl(userName, period)
                    }
            }
    }

    private fun generateUrl(userName: String, timeStampPeriod: TimeStampPeriod): String {
        return "https://www.last.fm/user/$userName/library/artists?date_preset=${
            timeStampPeriodMapper.getPeriodQueryValue(
                timeStampPeriod
            )
        }"
    }
}
