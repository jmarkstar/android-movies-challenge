package com.jmarkstar.princestheatre

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MoviesApplication : Application() {

    @Inject
    lateinit var imagePipelineConfig: ImagePipelineConfig

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this, imagePipelineConfig)
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
