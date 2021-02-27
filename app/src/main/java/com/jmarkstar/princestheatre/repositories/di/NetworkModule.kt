package com.jmarkstar.princestheatre.repositories.di

import android.content.Context
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.gson.Gson
import com.jmarkstar.princestheatre.common.Constants
import com.jmarkstar.princestheatre.repositories.network.MoviesService
import com.jmarkstar.princestheatre.repositories.network.interceptors.AddApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMoviesService(
        retrofit: Retrofit
    ): MoviesService = retrofit.create(MoviesService::class.java)

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        addApiKeyInterceptor: AddApiKeyInterceptor
    ): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").i(message)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(addApiKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiKeyInterceptor() = AddApiKeyInterceptor()

    @Singleton
    @Provides
    fun provideRetrofitClient(
        gson: Gson,
        okHttpClient: OkHttpClient,
        baseApiUrl: HttpUrl
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideImagePipelineConfig(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImagePipelineConfig {

        val diskCacheLowSizeBytes = (1024 * 1024 * 50).toLong() // 50 MB
        val diskCacheSizeBytes = (1024 * 1024 * 500).toLong() // 500 MB

        val diskCacheConfig = DiskCacheConfig.newBuilder(context)
            .setMaxCacheSizeOnLowDiskSpace(diskCacheLowSizeBytes)
            .setMaxCacheSize(diskCacheSizeBytes)
            .setVersion(1)
            .build()

        return OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient)
            .setMainDiskCacheConfig(diskCacheConfig)
            .setDiskCacheEnabled(true)
            .setHttpConnectionTimeout(Constants.HTTP_TIMEOUT.toInt())
            .build()
    }
}
