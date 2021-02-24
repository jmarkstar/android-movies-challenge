package com.jmarkstar.princestheatre.di

import android.content.Context
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.gson.Gson
import com.jmarkstar.princestheatre.BuildConfig
import com.jmarkstar.princestheatre.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    // NETWORK

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").i(message)
                }
            }
        )
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    fun provideImagePipelineConfig(@ApplicationContext context: Context, okHttpClient: OkHttpClient): ImagePipelineConfig {

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
