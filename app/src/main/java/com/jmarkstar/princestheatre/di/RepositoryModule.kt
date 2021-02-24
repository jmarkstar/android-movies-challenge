package com.jmarkstar.princestheatre.di

import com.jmarkstar.princestheatre.repositories.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class RepositoryModule {

    // REPOSITORY

    // API

    // DAO

    @Provides
    fun provideMovieDao(database: MoviesDatabase) = database.movieDao

    @Provides
    fun provideProviderDao(database: MoviesDatabase) = database.providerDao
}
