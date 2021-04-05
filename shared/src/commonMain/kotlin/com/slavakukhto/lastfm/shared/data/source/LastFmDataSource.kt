package com.slavakukhto.lastfm.shared.data.source

import com.slavakukhto.lastfm.shared.API_KEY
import com.slavakukhto.lastfm.shared.data.responseentity.*
import io.ktor.client.*
import io.ktor.client.request.*

interface ApiService {

    suspend fun auth(userName: String, password: String, apiSign: String): String

    suspend fun getUserInfo(userName: String): UserInfoResponse

    suspend fun getUserRecentTracks(userName: String): UserRecentTracksResponse

    suspend fun getUserAlbums(userName: String, period: String): UserTopAlbumsResponse

    suspend fun getUserArtists(userName: String, period: String): UserTopArtistsResponse

    suspend fun getUserTracks(userName: String, period: String): UserTopTracksResponse

    suspend fun getAlbum(artist: String, album: String): AlbumResponse

    suspend fun getArtist(artist: String): ArtistResponse

    suspend fun getTrack(artist: String, song: String): TrackResponse
}

class LastFmApiService(private val httpClient: HttpClient) : ApiService {

    override suspend fun auth(userName: String, password: String, apiSign: String): String {
        return httpClient.post(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_SESSION)
                parameters.append(KEY_USERNAME, userName)
                parameters.append(KEY_PASSWORD, password)
                parameters.append(KEY_API_SIGN, apiSign)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
            }
        }
    }

    override suspend fun getUserInfo(userName: String): UserInfoResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_USER_INFO)
                parameters.append(KEY_USER, userName)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
            }
        }
    }

    override suspend fun getUserRecentTracks(userName: String): UserRecentTracksResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_USER_RECENT_TRACKS)
                parameters.append(KEY_USER, userName)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
            }
        }
    }

    override suspend fun getUserAlbums(userName: String, period: String): UserTopAlbumsResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_USER_TOP_ALBUMS)
                parameters.append(KEY_USER, userName)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
                parameters.append(KEY_PERIOD, period)
            }
        }
    }

    override suspend fun getUserArtists(userName: String, period: String): UserTopArtistsResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_USER_TOP_ARTISTS)
                parameters.append(KEY_USER, userName)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
                parameters.append(KEY_PERIOD, period)
            }
        }
    }

    override suspend fun getUserTracks(userName: String, period: String): UserTopTracksResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_USER_TOP_TRACKS)
                parameters.append(KEY_USER, userName)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
                parameters.append(KEY_PERIOD, period)
            }
        }
    }

    override suspend fun getAlbum(artist: String, album: String): AlbumResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_ALBUM_INFO)
                parameters.append(KEY_ARTIST, artist)
                parameters.append(KEY_ALBUM, album)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
            }
        }
    }

    override suspend fun getArtist(artist: String): ArtistResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_ARTIST_INFO)
                parameters.append(KEY_ARTIST, artist)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
            }
        }
    }

    override suspend fun getTrack(artist: String, song: String): TrackResponse {
        return httpClient.get(BASE_URL) {
            url {
                parameters.append(KEY_METHOD, PARAM_METHOD_GET_TRACK_INFO)
                parameters.append(KEY_ARTIST, artist)
                parameters.append(KEY_TRACK, song)
                parameters.append(KEY_FORMAT, PARAM_FORMAT)
                parameters.append(KEY_API, API_KEY)
            }
        }
    }

    private companion object {

        private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
        private const val KEY_METHOD = "method"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_API_SIGN = "api_sig"
        private const val KEY_FORMAT = "format"
        private const val KEY_API = "api_key"
        private const val KEY_USER = "user"
        private const val KEY_PERIOD = "period"
        private const val KEY_ARTIST = "artist"
        private const val KEY_ALBUM = "album"
        private const val KEY_TRACK = "track"
        private const val PARAM_FORMAT = "json"
        private const val PARAM_METHOD_GET_SESSION = "auth.getMobileSession"
        private const val PARAM_METHOD_GET_USER_INFO = "user.getinfo"
        private const val PARAM_METHOD_GET_USER_RECENT_TRACKS = "user.getrecenttracks"
        private const val PARAM_METHOD_GET_USER_TOP_ALBUMS = "user.gettopalbums"
        private const val PARAM_METHOD_GET_USER_TOP_ARTISTS = "user.gettopartists"
        private const val PARAM_METHOD_GET_USER_TOP_TRACKS = "user.gettoptracks"
        private const val PARAM_METHOD_GET_ALBUM_INFO = "album.getinfo"
        private const val PARAM_METHOD_GET_ARTIST_INFO = "artist.getinfo"
        private const val PARAM_METHOD_GET_TRACK_INFO = "track.getinfo"
    }
}
