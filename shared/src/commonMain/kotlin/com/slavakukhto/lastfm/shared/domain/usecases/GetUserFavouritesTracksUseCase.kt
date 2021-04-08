package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.slavakukhto.lastfm.shared.domain.models.FavouriteArtist
import com.slavakukhto.lastfm.shared.domain.models.FavouriteTrack
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class GetUserFavouritesTracksUseCase(
    private val userRepository: UserRepository,
    private val mediaRepository: MediaRepository
) {

    fun execute(): Single<List<FavouriteTrack>> {
        return userRepository.getUserName()
            .flatMap { userName ->
                userRepository.getUserTimeStamp()
                    .flatMap { timePeriod ->
                        mediaRepository.getUserFavouriteTracks(userName, timePeriod)
                    }
            }
    }
}
