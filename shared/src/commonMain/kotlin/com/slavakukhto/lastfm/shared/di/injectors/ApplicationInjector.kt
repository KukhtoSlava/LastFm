package com.slavakukhto.lastfm.shared.di.injectors

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
var appContext: Any? = null

fun provideContext(context: Any?): Any? {
    appContext = context
    return context
}
