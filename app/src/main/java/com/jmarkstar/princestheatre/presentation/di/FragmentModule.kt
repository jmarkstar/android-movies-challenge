package com.jmarkstar.princestheatre.presentation.di

import com.jmarkstar.princestheatre.presentation.movie_list.MovieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun provideMovieAdapter() = MovieAdapter()
}
