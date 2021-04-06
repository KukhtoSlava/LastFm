package com.slavakukhto.lastfm.shared.rxhelpers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze

actual val defaultRxDispatcher: CoroutineContext
    get() = MainDispatcher

actual val uiRxDispatcher: CoroutineContext
    get() = MainDispatcher


@ThreadLocal
private object MainDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run().freeze()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}
