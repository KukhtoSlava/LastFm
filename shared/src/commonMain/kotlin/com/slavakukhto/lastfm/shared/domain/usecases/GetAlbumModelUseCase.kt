package com.slavakukhto.lastfm.shared.domain.usecases

import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.domain.models.AlbumModel
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository

class GetAlbumModelUseCase(
    private val mediaRepository: MediaRepository
) {

    fun execute(artist: String, album: String): Single<AlbumModel> {
        return mediaRepository.getAlbum(artist, album)
    }
}
