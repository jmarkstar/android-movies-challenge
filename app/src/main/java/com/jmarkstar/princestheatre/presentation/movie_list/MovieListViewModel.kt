package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jmarkstar.princestheatre.domain.doIfFailure
import com.jmarkstar.princestheatre.domain.doIfSuccess
import com.jmarkstar.princestheatre.domain.models.MovieModel
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import com.jmarkstar.princestheatre.presentation.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val savedStateHandle: SavedStateHandle? = null,
) : ViewModel() {

    val movies = liveData<Resource<List<MovieModel>>> {
        emit(Resource.Loading())
        val result = getMoviesUseCase.invoke()
        result.doIfSuccess {
            emit(Resource.Success(it))
        }
        result.doIfFailure {
            emit(Resource.Error(it?.message))
        }
    }
}
