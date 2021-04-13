package com.slavakukhto.lastfm.shared.data.repositoryimpl

import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.data.mappers.*
import com.slavakukhto.lastfm.shared.data.source.ApiService
import com.slavakukhto.lastfm.shared.data.source.ParseYouTubeSource
import com.slavakukhto.lastfm.shared.domain.models.*
import com.slavakukhto.lastfm.shared.domain.repository.MediaRepository
import com.slavakukhto.lastfm.shared.rxhelpers.singleFromCoroutineUnsafe
import com.slavakukhto.lastfm.shared.rxhelpers.uiRxDispatcher

class MediaRepositoryImpl(
    private val apiService: ApiService,
    private val userScrobblesTracksMapper: UserScrobblesTracksMapper,
    private val userFavouriteTracksMapper: UserFavouriteTracksMapper,
    private val userFavouriteArtistsMapper: UserFavouriteArtistsMapper,
    private val userFavouriteAlbumsMapper: UserFavouriteAlbumsMapper,
    private val timeStampPeriodMapper: TimeStampPeriodMapper,
    private val trackMapper: TrackMapper,
    private val artistMapper: ArtistMapper,
    private val albumMapper: AlbumMapper,
    private val parseYouTubeSource: ParseYouTubeSource
) : MediaRepository {

    override fun getUserScrobblesTracks(userName: String): Single<List<ScrobblesTrack>> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            userScrobblesTracksMapper.transform(apiService.getUserRecentTracks(userName))
        }
    }

    override fun getUserFavouriteArtists(userName: String, period: TimeStampPeriod): Single<List<FavouriteArtist>> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            val queryPeriod = timeStampPeriodMapper.getPeriodTopValuesQuery(period)
            userFavouriteArtistsMapper.transform(apiService.getUserArtists(userName, queryPeriod))
        }
    }

    override fun getUserFavouriteAlbums(userName: String, period: TimeStampPeriod): Single<List<FavouriteAlbum>> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            val queryPeriod = timeStampPeriodMapper.getPeriodTopValuesQuery(period)
            userFavouriteAlbumsMapper.transform(apiService.getUserAlbums(userName, queryPeriod))
        }
    }

    override fun getUserFavouriteTracks(userName: String, period: TimeStampPeriod): Single<List<FavouriteTrack>> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            val queryPeriod = timeStampPeriodMapper.getPeriodTopValuesQuery(period)
            userFavouriteTracksMapper.transform(apiService.getUserTracks(userName, queryPeriod))
        }
    }

    override fun getTrack(track: String, artist: String): Single<TrackModel> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            trackMapper.transform(apiService.getTrack(artist, track))
        }
    }

    override fun getArtist(artist: String): Single<ArtistModel> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            artistMapper.transform(apiService.getArtist(artist))
        }
    }

    override fun getAlbum(artist: String, album: String): Single<AlbumModel> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            albumMapper.transform(apiService.getAlbum(artist, album))
        }
    }

    override fun getLinkBody(url: String): Single<String> {
        return singleFromCoroutineUnsafe(uiRxDispatcher) {
            parseYouTubeSource.getBody(url)
        }
    }
}
