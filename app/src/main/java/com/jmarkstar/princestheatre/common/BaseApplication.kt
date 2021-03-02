package com.jmarkstar.princestheatre.common

import android.app.Application
import com.jmarkstar.princestheatre.BuildConfig
import com.jmarkstar.princestheatre.R
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber

// I was thinking to initialize Fresco on this `BaseApplication` and reused
// this class for testing purposes as well but I'm getting this error: *IllegalStateException: The component was not created. Check that you have added the HiltAndroidRule*.
// ad the explanation why this is happening is this github discussion link -> https://github.com/google/dagger/issues/2033#issuecomment-671578781
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupAppLogging()
        setupFonts()
    }

    private fun setupAppLogging() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun setupFonts() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/SF-UI-Text-Regular.otf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                ).build()
        )
    }
}
