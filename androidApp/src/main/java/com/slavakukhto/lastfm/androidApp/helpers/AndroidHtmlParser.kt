package com.slavakukhto.lastfm.androidApp.helpers

import com.slavakukhto.lastfm.shared.resolvers.HtmlParser
import org.jsoup.Jsoup
import java.util.regex.Matcher
import java.util.regex.Pattern

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

    override fun findArtistImage(responseBody: String): String {
        val pattern: Pattern =
            Pattern.compile("class=\"header-new-background-image\".*\\s*.*\\s*.*\\s*content=\"(.*)\".*\\s*></div>")
        val matcher: Matcher = pattern.matcher(responseBody)
        val result = matcher.find()
        return if (result) {
            matcher.toMatchResult().group(1)
        } else {
            ""
        }
    }
}
