package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel()
