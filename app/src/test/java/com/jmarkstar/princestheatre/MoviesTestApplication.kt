package com.jmarkstar.princestheatre

import com.facebook.drawee.backends.pipeline.Fresco
import com.jmarkstar.princestheatre.common.BaseApplication

/** Will initialize Fresco without any image pipeline configuration
 * */
open class MoviesTestApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
    }
}
