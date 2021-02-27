package com.jmarkstar.princestheatre.common

import android.os.Build
import androidx.annotation.CallSuper
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1], application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
abstract class BaseTest {

    val testMainDispatcher = TestCoroutineDispatcher()

    @CallSuper
    @Before
    open fun setUp() {
        Dispatchers.setMain(testMainDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testMainDispatcher.cleanupTestCoroutines()
    }
}
