package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.UserTopTracksResponse
import com.slavakukhto.lastfm.shared.domain.models.FavouriteTrack

class UserFavouriteTracksMapper {

    fun transform(userTopTracksResponse: UserTopTracksResponse): List<FavouriteTrack> {
        val list = mutableListOf<FavouriteTrack>()
        val tracks = userTopTracksResponse.toptracks.track
        tracks.forEach { data ->
            val track = data.name
            val artist = data.artist?.name ?: "Unknown"
            val scrobbles = data.playcount.toInt()
            val path = data.image?.get(3)?.text
            val imagePath = if (path.isNullOrEmpty()) {
                "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
            } else {
                path
            }
            val favouriteTrack = FavouriteTrack(track, artist, scrobbles, imagePath)
            list.add(favouriteTrack)
        }
        return list
    }
}
