package com.slavakukhto.lastfm.shared.domain.repository

import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.domain.models.*

interface MediaRepository {

    fun getUserScrobblesTracks(userName: String): Single<List<ScrobblesTrack>>

    fun getUserFavouriteArtists(userName: String, period: TimeStampPeriod): Single<List<FavouriteArtist>>

    fun getUserFavouriteAlbums(userName: String, period: TimeStampPeriod): Single<List<FavouriteAlbum>>

    fun getUserFavouriteTracks(userName: String, period: TimeStampPeriod): Single<List<FavouriteTrack>>

    fun getTrack(track: String, artist: String): Single<TrackModel>

    fun getArtist(artist: String): Single<ArtistModel>

    fun getAlbum(artist: String, album: String): Single<AlbumModel>

    fun getLinkBody(url: String): Single<String>
}
