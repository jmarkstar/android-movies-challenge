package com.jmarkstar.princestheatre.di

import com.jmarkstar.princestheatre.common.UnitTestUtils
import com.jmarkstar.princestheatre.common.coroutines.DispatcherProvider
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.common.util.NetworkStateImp
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.HttpUrl
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApplicationModule::class]
)
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

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideDispatcherProvider(
        testCoroutineDispatcher: TestCoroutineDispatcher
    ) = object : DispatcherProvider {

        override val Main: CoroutineDispatcher = testCoroutineDispatcher
        override val IO: CoroutineDispatcher = testCoroutineDispatcher
    }
}
