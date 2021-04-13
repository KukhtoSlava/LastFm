package com.slavakukhto.lastfm.shared.resolvers

interface HtmlParser {

    fun findYouTubeLink(responseBody: String): String
}
