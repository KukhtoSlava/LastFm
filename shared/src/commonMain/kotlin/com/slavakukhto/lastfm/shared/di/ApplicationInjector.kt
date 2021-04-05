package com.slavakukhto.lastfm.shared.di

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ApplicationInjector {

    var context: Any? = null

    fun provideContext(context: Any?): Any? {
        this.context = context
        return context
    }
}
