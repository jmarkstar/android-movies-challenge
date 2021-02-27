package com.jmarkstar.princestheatre

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupAppLogging()
    }

    private fun setupAppLogging() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
