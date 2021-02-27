package com.jmarkstar.princestheatre.presentation.di

import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCasesModule {

    @ViewModelScoped
    @Binds
    abstract fun provideGetMoviesUseCase(
        getMoviesUseCaseImpl: GetMoviesUseCaseImpl
    ): GetMoviesUseCase
}
