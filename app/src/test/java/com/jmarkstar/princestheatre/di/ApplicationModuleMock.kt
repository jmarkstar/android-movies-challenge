package com.jmarkstar.princestheatre.di

import com.jmarkstar.princestheatre.common.UnitTestUtils
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.common.util.NetworkStateImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.HttpUrl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModuleMock {

    @Singleton
    @Provides
    fun provideBaseApiUrl(): HttpUrl = HttpUrl.Builder()
        .scheme("http")
        .host("localhost")
        .port(UnitTestUtils.MOCK_SERVER_PORT)
        .build()

    @Singleton
    @Provides
    fun provideNetworkState(): NetworkState = NetworkStateImp()
}
