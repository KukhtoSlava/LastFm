package com.slavakukhto.lastfm.shared.data.mappers

import com.slavakukhto.lastfm.shared.data.responseentity.UserTopAlbumsResponse
import com.slavakukhto.lastfm.shared.domain.models.FavouriteAlbum

class UserFavouriteAlbumsMapper {

    fun transform(userTopAlbumsResponse: UserTopAlbumsResponse): List<FavouriteAlbum> {
        val list = mutableListOf<FavouriteAlbum>()
        val albums = userTopAlbumsResponse.topalbums.album
        albums.forEach { data ->
            val album = data.name
            val scrobbles = data.playcount.toInt()
            val path = data.image?.get(3)?.text
            val imagePath = if (path.isNullOrEmpty()) {
                "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
            } else {
                path
            }
            val favouriteAlbum = FavouriteAlbum(album, scrobbles, imagePath)
            list.add(favouriteAlbum)
        }
        return list
    }
}
