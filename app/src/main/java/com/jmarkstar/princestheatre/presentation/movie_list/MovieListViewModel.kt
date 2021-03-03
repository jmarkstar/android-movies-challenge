package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmarkstar.princestheatre.domain.doIfFailure
import com.jmarkstar.princestheatre.domain.doIfSuccess
import com.jmarkstar.princestheatre.domain.models.MovieModel
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import com.jmarkstar.princestheatre.presentation.common.Resource
import com.jmarkstar.princestheatre.common.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val savedStateHandle: SavedStateHandle? = null,
) : ViewModel() {

    val movies: LiveData<Resource<List<MovieModel>>>
        get() = _movies

    private val _movies = MutableLiveData<Resource<List<MovieModel>>>()

    init {
        loadMovies()
    }

    fun loadMovies() {
        _movies.postValue(Resource.Loading())

        viewModelScope.launch(dispatcherProvider.IO) {

            val result = getMoviesUseCase.invoke()
            result.doIfSuccess {
                _movies.postValue(Resource.Success(it))
            }
            result.doIfFailure {
                _movies.postValue(Resource.Error(it?.message))
            }
        }
    }
}
