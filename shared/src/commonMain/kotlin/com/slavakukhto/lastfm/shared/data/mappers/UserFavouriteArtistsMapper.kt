package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.UserTopArtistsResponse
import com.slavakukhto.lastfm.shared.domain.models.FavouriteArtist

class UserFavouriteArtistsMapper {

    fun transform(userTopArtistsResponse: UserTopArtistsResponse): List<FavouriteArtist> {
        val list = mutableListOf<FavouriteArtist>()
        val artists = userTopArtistsResponse.topartists.artist
        artists.forEach { data ->
            val artist = data.name
            val scrobbles = data.playcount.toInt()
            val path = data.image?.get(3)?.text
            val imagePath = if (path.isNullOrEmpty()) {
                "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
            } else {
                path
            }
            val favouriteArtist = FavouriteArtist(artist, scrobbles, imagePath)
            list.add(favouriteArtist)
        }
        return list
    }
}
