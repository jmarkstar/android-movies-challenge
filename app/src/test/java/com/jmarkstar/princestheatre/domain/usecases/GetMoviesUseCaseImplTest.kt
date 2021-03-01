package com.jmarkstar.princestheatre.domain.usecases

import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import com.jmarkstar.princestheatre.fakeMovies
import com.jmarkstar.princestheatre.repositories.entities.toModels
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseImplTest : BaseTest() {

    private val providerRepository = mockk<ProviderRepository>()

    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setUp() {
        getMoviesUseCase = GetMoviesUseCaseImpl(providerRepository)
    }

    @Test
    fun `test get movies Use Case when it's successfully`() = runBlocking {

        // Given
        val fakeMovieModels = fakeMovies.toModels().toSet().toList()

        coEvery { providerRepository.getMovies() } returns ResultOf.Success(fakeMovieModels)

        // When
        val getMoviesUseCaseResult = getMoviesUseCase.invoke()

        // Then
        assert(getMoviesUseCaseResult is ResultOf.Success)
        val list = (getMoviesUseCaseResult as ResultOf.Success).value
        assertEquals(fakeMovieModels.size, list.size)
    }

    @Test
    fun `test get movies Use Case when it's empty list`() = runBlocking {

        // Given
        coEvery { providerRepository.getMovies() } returns ResultOf.Success(emptyList())

        // When
        val getMoviesUseCaseResult = getMoviesUseCase.invoke()

        // Then
        assert(getMoviesUseCaseResult is ResultOf.Success)
        val list = (getMoviesUseCaseResult as ResultOf.Success).value
        assertEquals(0, list.size)
    }

    @Test
    fun `test get movies Use Case when error happens`() = runBlocking {

        // Given
        coEvery { providerRepository.getMovies() } returns ResultOf.Failure(Exception())

        // When
        val getMoviesUseCaseResult = getMoviesUseCase.invoke()

        // Then
        assert(getMoviesUseCaseResult is ResultOf.Failure)
    }
}
