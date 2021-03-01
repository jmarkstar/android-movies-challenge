package com.jmarkstar.princestheatre.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.jmarkstar.princestheatre.BuildConfig
import com.jmarkstar.princestheatre.common.coroutines.CoroutineDispatcherProvider
import com.jmarkstar.princestheatre.common.coroutines.DispatcherProvider
import com.jmarkstar.princestheatre.common.util.NetworkCallbackImp
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.common.util.NetworkStateImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideBaseApiUrl() = HttpUrl.Builder()
        .scheme(BuildConfig.API_SCHEMA)
        .host(BuildConfig.API_HOST)
        .build()

    @Singleton
    @Provides
    fun provideNetworkState(@ApplicationContext context: Context): NetworkState {
        val holder = NetworkStateImp()
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            NetworkCallbackImp(
                holder
            )
        )
        return holder
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = CoroutineDispatcherProvider()
}
