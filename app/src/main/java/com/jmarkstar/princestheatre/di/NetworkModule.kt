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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/** I'm only using 1 module for the whole app because it's only a demo.
 * */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    // NETWORK

    @Provides
    @Singleton
    fun provideGson() = Gson()
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").i(message)
            }
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClientBuilder = OkHttpClient().newBuilder()
        httpClientBuilder.connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.readTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.writeTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
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
            .setHttpConnectionTimeout(30000)
            .build()
    }
}

