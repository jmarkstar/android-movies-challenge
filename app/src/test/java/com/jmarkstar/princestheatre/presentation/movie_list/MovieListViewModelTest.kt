package com.jmarkstar.princestheatre.presentation.movie_list

import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import com.jmarkstar.princestheatre.fakeMovies
import com.jmarkstar.princestheatre.presentation.common.Resource
import com.jmarkstar.princestheatre.repositories.entities.toModels
import com.jmarkstar.princestheatre.repositories.exceptions.NetworkException
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListViewModelTest : BaseTest() {

    private val getMoviesUseCase = mockk<GetMoviesUseCase>()

    private lateinit var movieListViewModel: MovieListViewModel

    override fun setUp() {
        super.setUp()
        movieListViewModel = MovieListViewModel(getMoviesUseCase)
    }

    @Test
    fun `test get movies View Model successfully`() {

        // Given
        val mockResult = ResultOf.Success(fakeMovies.toModels().toSet().toList())
        coEvery { getMoviesUseCase.invoke() } returns mockResult

        movieListViewModel.movies.observeForever {}

        // When
        val moviesResource = movieListViewModel.movies.value

        // Then
        coVerify { getMoviesUseCase.invoke() }

        assert(moviesResource is Resource.Success)
        val sucessResource = moviesResource as Resource.Success
        assertEquals(mockResult.value, sucessResource.data)
    }

    @Test
    fun `test get movies View Model failure`() {

        // Given
        val mockResult = ResultOf.Failure(NetworkException(502, "Bad Gateway"))
        coEvery { getMoviesUseCase.invoke() } returns mockResult

        movieListViewModel.movies.observeForever {}

        // When
        val moviesResource = movieListViewModel.movies.value

        // Then
        coVerify { getMoviesUseCase.invoke() }

        assert(moviesResource is Resource.Error)
        val failedResource = moviesResource as Resource.Error

        assertEquals(mockResult.throwable?.message, failedResource.message)
    }
}
