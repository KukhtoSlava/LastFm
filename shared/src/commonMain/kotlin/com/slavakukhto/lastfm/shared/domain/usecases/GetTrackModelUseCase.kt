package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.badoo.reaktive.single.map
import com.slavakukhto.lastfm.shared.domain.models.TrackModel
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.resolvers.HtmlParser

class GetTrackModelUseCase(
    private val mediaRepository: MediaRepository,
    private val htmlParser: HtmlParser
) {

    fun execute(track: String, artist: String): Single<TrackModel> {
        return mediaRepository.getTrack(track, artist)
            .flatMap { trackModel ->
                mediaRepository.getLinkBody(trackModel.url)
                    .map { body ->
                        val youtubeLink = parseBody(body)
                        trackModel.copy(youtubeLink = youtubeLink)
                    }
            }
    }

    private fun parseBody(body: String): String {
        return htmlParser.findYouTubeLink(body)
    }
}
