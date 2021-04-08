package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.slavakukhto.lastfm.shared.domain.models.FavouriteAlbum
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class GetUserFavouritesAlbumsUseCase(
    private val userRepository: UserRepository,
    private val mediaRepository: MediaRepository
) {

    fun execute(): Single<List<FavouriteAlbum>> {
        return userRepository.getUserName()
            .flatMap { userName ->
                userRepository.getUserTimeStamp()
                    .flatMap { timePeriod ->
                        mediaRepository.getUserFavouriteAlbums(userName, timePeriod)
                    }
            }
    }
}
