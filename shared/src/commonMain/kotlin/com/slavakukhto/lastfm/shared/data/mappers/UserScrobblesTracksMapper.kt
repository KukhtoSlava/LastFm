package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.UserRecentTracksResponse
import com.slavakukhto.lastfm.shared.domain.models.ScrobblesTrack

class UserScrobblesTracksMapper {

    fun transform(userRecentTracksResponse: UserRecentTracksResponse): List<ScrobblesTrack> {
        val list = mutableListOf<ScrobblesTrack>()
        val tracks = userRecentTracksResponse.recenttracks.track
        tracks.forEach { data ->
            val track = data.name ?: "Unknown"
            val artist = data.artist.text
            val path = data.image?.get(3)?.text
            val imagePath = if (path.isNullOrEmpty()) {
                "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
            } else {
                path
            }
            val date = data.date?.text ?: ""
            val scrobblesTrack = ScrobblesTrack(track, artist, imagePath, date)
            list.add(scrobblesTrack)
        }
        return list
    }
}
