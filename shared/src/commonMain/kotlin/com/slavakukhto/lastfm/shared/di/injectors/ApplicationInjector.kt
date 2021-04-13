package com.slavakukhto.lastfm.shared.di.injectors

import com.slavakukhto.lastfm.shared.resolvers.HtmlParser
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
var appContext: Any? = null

@ThreadLocal
lateinit var htmlParser: HtmlParser

fun provideContext(context: Any?): Any? {
    appContext = context
    return context
}

fun provideHtmlParser(parser: HtmlParser): HtmlParser {
    htmlParser = parser
    return parser
}
