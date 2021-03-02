package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.common.CoroutineTestRule
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.models.MovieModel
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import com.jmarkstar.princestheatre.fakeMovies
import com.jmarkstar.princestheatre.presentation.common.Resource
import com.jmarkstar.princestheatre.repositories.entities.toModels
import com.jmarkstar.princestheatre.repositories.exceptions.NetworkException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MovieListViewModelTest : BaseTest() {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getMoviesUseCase = mockk<GetMoviesUseCase>()

    private lateinit var movieListViewModel: MovieListViewModel

    @Before
    fun setUp() {
        movieListViewModel = MovieListViewModel(getMoviesUseCase, coroutineTestRule.testDispatcherProvider)
    }

    @Test
    fun `test get movies View Model successfully`() {

        // Given
        val movies = fakeMovies.toModels().toSet().toList()
        val mockResult = ResultOf.Success(movies)

        coEvery { getMoviesUseCase.invoke() } returns mockResult

        // When
        movieListViewModel.loadMovies()
        coroutineTestRule.testDispatcher.advanceUntilIdle()

        // Then
        coVerify { getMoviesUseCase.invoke() }

        val moviesResource = movieListViewModel.movies.value
        assert(moviesResource is Resource.Success<List<MovieModel>>)
        val successResource = moviesResource as Resource.Success<List<MovieModel>>
        assertEquals(mockResult.value, successResource.data)
    }

    @Test
    fun `test get movies View Model failure`() {

        // Given
        val mockResult = ResultOf.Failure(NetworkException(502, "Bad Gateway"))
        coEvery { getMoviesUseCase.invoke() } returns mockResult

        // When
        movieListViewModel.loadMovies()
        coroutineTestRule.testDispatcher.advanceUntilIdle()

        // Then
        coVerify { getMoviesUseCase.invoke() }

        val moviesResource = movieListViewModel.movies.value
        assert(moviesResource is Resource.Error<List<MovieModel>>)
        val failedResource = moviesResource as Resource.Error<List<MovieModel>>
        assertEquals(mockResult.throwable?.message, failedResource.message)
    }
}
