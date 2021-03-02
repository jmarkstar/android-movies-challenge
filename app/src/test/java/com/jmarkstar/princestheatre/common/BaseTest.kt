package com.jmarkstar.princestheatre.common

import android.os.Build
import com.jmarkstar.princestheatre.HiltTestApplication_Application
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1], application = HiltTestApplication_Application::class)
@RunWith(RobolectricTestRunner::class)
abstract class BaseTest
