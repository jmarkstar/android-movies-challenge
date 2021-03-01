package com.jmarkstar.princestheatre.common

import com.jmarkstar.princestheatre.common.coroutines.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineDispatcherProvider(
    testCoroutineDispatcher: TestCoroutineDispatcher
) : DispatcherProvider {

    override val Main: CoroutineContext = testCoroutineDispatcher
    override val IO: CoroutineContext = testCoroutineDispatcher
}
