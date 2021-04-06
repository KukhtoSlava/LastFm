package com.slavakukhto.lastfm.shared.rxhelpers

import com.badoo.reaktive.coroutinesinterop.asDisposable
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun <T> singleFromCoroutineUnsafe(mainContext: CoroutineContext, block: suspend CoroutineScope.() -> T): Single<T> =
    single { emitter ->
        GlobalScope
            .launch(mainContext) {
                try {
                    emitter.onSuccess(block())
                } catch (e: Throwable) {
                    emitter.onError(e)
                }
            }
            .asDisposable()
            .also(emitter::setDisposable)
    }

expect val defaultRxDispatcher: CoroutineContext
expect val uiRxDispatcher: CoroutineContext
