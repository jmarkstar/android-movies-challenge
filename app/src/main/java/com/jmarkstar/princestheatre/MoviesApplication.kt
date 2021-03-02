package com.jmarkstar.princestheatre

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.jmarkstar.princestheatre.common.BaseApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MoviesApplication : BaseApplication() {

    @Inject
    lateinit var imagePipelineConfig: ImagePipelineConfig

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this, imagePipelineConfig)
    }
}
