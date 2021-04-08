package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.slavakukhto.lastfm.shared.domain.models.ScrobblesTrack
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class GetUserScrobblesTracksUseCase(
    private val userRepository: UserRepository,
    private val mediaRepository: MediaRepository
) {

    fun execute(): Single<List<ScrobblesTrack>> {
        return userRepository.getUserName()
            .flatMap { userName ->
                mediaRepository.getUserScrobblesTracks(userName)
            }
    }
}
