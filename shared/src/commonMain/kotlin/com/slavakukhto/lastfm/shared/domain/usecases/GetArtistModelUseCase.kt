package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.flatMap
import com.badoo.reaktive.single.map
import com.slavakukhto.lastfm.shared.domain.models.ArtistModel
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.resolvers.HtmlParser

class GetArtistModelUseCase(
    private val mediaRepository: MediaRepository,
    private val htmlParser: HtmlParser
) {

    fun execute(artist: String): Single<ArtistModel> {
        return mediaRepository.getArtist(artist)
            .flatMap { trackModel ->
                mediaRepository.getLinkBody(trackModel.url)
                    .map { body ->
                        val imagePath = parseBody(body)
                        trackModel.copy(imagePath = imagePath)
                    }
            }
    }

    private fun parseBody(body: String): String {
        return htmlParser.findArtistImage(body)
    }
}
