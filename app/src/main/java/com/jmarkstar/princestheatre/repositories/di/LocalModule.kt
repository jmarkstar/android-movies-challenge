package com.jmarkstar.princestheatre.repositories.di

import android.content.Context
import androidx.room.Room
import com.jmarkstar.princestheatre.BuildConfig
import com.jmarkstar.princestheatre.common.security.Passphrases
import com.jmarkstar.princestheatre.repositories.local.MoviesDatabase
import com.jmarkstar.princestheatre.repositories.local.PrepopulateCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabasePopulateCallback() = PrepopulateCallback()

    @Singleton
    @Provides
    fun provideSupportFactory(): SupportFactory? {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes(Passphrases.dbPassphrase.toCharArray())
        return if (BuildConfig.enableEncryption) SupportFactory(passphrase) else null
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        prepopulateCallback: PrepopulateCallback,
        supportFactory: SupportFactory? = null
    ) =
        Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db"
        )
            .openHelperFactory(supportFactory)
            .addCallback(prepopulateCallback)
            .build()

    @Provides
    fun provideMovieDao(database: MoviesDatabase) = database.movieDao

    @Provides
    fun provideProviderDao(database: MoviesDatabase) = database.providerDao
}
