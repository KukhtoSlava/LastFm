package com.slavakukhto.lastfm.androidApp.helpers

import com.slavakukhto.lastfm.shared.resolvers.HtmlParser
import org.jsoup.Jsoup

class AndroidHtmlParser : HtmlParser {

    override fun findYouTubeLink(responseBody: String): String {
        var link = ""
        val document = Jsoup.parse(responseBody)
        val elements = document.select("a.image-overlay-playlink-link")
        elements.filterNotNull().forEach {
            val baseUrl = it.attr("href")
            if (baseUrl.contains("https://www.youtube.com")) {
                link = baseUrl
                return link
            }
        }
        return link
    }
}
