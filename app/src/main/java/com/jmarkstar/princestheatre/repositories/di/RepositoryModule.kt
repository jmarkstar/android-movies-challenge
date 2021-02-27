package com.jmarkstar.princestheatre.repositories.di

import com.jmarkstar.princestheatre.domain.repositories.MovieRepository
import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import com.jmarkstar.princestheatre.repositories.MovieRepositoryImpl
import com.jmarkstar.princestheatre.repositories.ProviderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun provideMovieProviderRepository(
        providerRepositoryImpl: ProviderRepositoryImpl
    ): ProviderRepository

    @ActivityRetainedScoped
    @Binds
    abstract fun provideMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}
