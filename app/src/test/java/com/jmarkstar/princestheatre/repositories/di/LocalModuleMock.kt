package com.jmarkstar.princestheatre.repositories.di

import android.content.Context
import androidx.room.Room
import com.jmarkstar.princestheatre.repositories.local.MoviesDatabase
import com.jmarkstar.princestheatre.repositories.local.PrepopulateCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalModule::class]
)
object LocalModuleMock {

    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): MoviesDatabase =
        Room.inMemoryDatabaseBuilder(
            appContext,
            MoviesDatabase::class.java
        )
            .addCallback(PrepopulateCallback())
            .build()

    @Provides
    fun provideMovieDao(database: MoviesDatabase) = database.movieDao

    @Provides
    fun provideProviderDao(database: MoviesDatabase) = database.providerDao
}
