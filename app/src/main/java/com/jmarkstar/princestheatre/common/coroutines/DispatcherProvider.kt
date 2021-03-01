package com.jmarkstar.princestheatre.common.coroutines

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    val Main: CoroutineContext
    val IO: CoroutineContext
}
