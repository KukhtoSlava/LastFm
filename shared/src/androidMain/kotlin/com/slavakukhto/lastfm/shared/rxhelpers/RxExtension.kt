package com.slavakukhto.lastfm.shared.rxhelpers

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val defaultRxDispatcher: CoroutineContext
    get() = Dispatchers.Default

actual val uiRxDispatcher: CoroutineContext
    get() = Dispatchers.Main
