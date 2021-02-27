package com.jmarkstar.princestheatre.repositories

import com.google.gson.Gson
import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.common.UnitTestUtils
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.di.ApplicationModule
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import com.jmarkstar.princestheatre.fakeMovies
import com.jmarkstar.princestheatre.repositories.di.LocalModule
import com.jmarkstar.princestheatre.repositories.exceptions.NetworkException
import com.jmarkstar.princestheatre.repositories.local.daos.MovieDao
import com.jmarkstar.princestheatre.repositories.local.daos.ProviderDao
import com.jmarkstar.princestheatre.repositories.network.MoviesService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(LocalModule::class, ApplicationModule::class)
@ExperimentalCoroutinesApi
class ProviderRepositoryImplTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var networkState: NetworkState

    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var providerDao: ProviderDao

    @Inject
    lateinit var moviesService: MoviesService

    private lateinit var providerRepository: ProviderRepository

    override fun setUp() {
        super.setUp()
        hiltRule.inject()
        mockWebServer.start(UnitTestUtils.MOCK_SERVER_PORT)

        // All the dependencies of `ProviderRepositoryImpl` are in the `ApplicationComponent` and
        // and this unit test is running under the Application context, so it is possible to reach them
        // but `ProviderRepository` is under the ` ActivityComponent`, if I want to get that instance
        // from Hilt, I would need to create a fake activity and that is that I don't want.
        providerRepository = ProviderRepositoryImpl(
            movieDao,
            providerDao,
            moviesService,
            networkState,
            gson
        )
    }

    @After
    fun stopMockServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test get movies when server responses successfully`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = object : Dispatcher() {

            override fun dispatch(
                request: RecordedRequest
            ): MockResponse {
                return when (request.path) {
                    "/api/cinemaworld/movies" ->
                        UnitTestUtils.mockJsonResponse(200, "get_movies_cinema_world_response_success.json")

                    "/api/filmworld/movies" ->
                        UnitTestUtils.mockJsonResponse(200, "get_movies_film_world_response_success.json")

                    else ->
                        UnitTestUtils.mockJsonResponse(404, "get_movies_by_provider_response_not_found_success.json")
                }
            }
        }

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Success)

        val movieModels = (resultOf as ResultOf.Success).value
        assertEquals(11, movieModels.size)
    }

    @Test
    fun `test get movies from local when there is not internet connection`() = runBlocking {
        // Given
        networkState.isConnected = false
        movieDao.insertAll(fakeMovies)

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Success)

        val movieModels = (resultOf as ResultOf.Success).value
        assertEquals(4, movieModels.size)
    }

    @Test
    fun `test get movies when an server error occurs in one provider endpoint`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = object : Dispatcher() {

            override fun dispatch(
                request: RecordedRequest
            ): MockResponse {
                return when (request.path) {
                    "/api/cinemaworld/movies" ->
                        UnitTestUtils.mockJsonResponse(200, "get_movies_cinema_world_response_success.json")

                    "/api/filmworld/movies" ->
                        UnitTestUtils.mockJsonResponse(502, "get_movies_by_provider_response_502_failed.json")

                    else ->
                        UnitTestUtils.mockJsonResponse(404, "get_movies_by_provider_response_not_found_success.json")
                }
            }
        }

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Success)

        val movieModels = (resultOf as ResultOf.Success).value
        assertEquals(11, movieModels.size)
    }

    @Test
    fun `test get movies when an server error occurs in all provider endpoints`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = object : Dispatcher() {

            override fun dispatch(
                request: RecordedRequest
            ): MockResponse {
                return when (request.path) {
                    "/api/cinemaworld/movies" ->
                        UnitTestUtils.mockJsonResponse(502, "get_movies_by_provider_response_502_failed.json")

                    "/api/filmworld/movies" ->
                        UnitTestUtils.mockJsonResponse(502, "get_movies_by_provider_response_502_failed.json")

                    else ->
                        UnitTestUtils.mockJsonResponse(404, "get_movies_by_provider_response_not_found_success.json")
                }
            }
        }

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Failure)
        val failureResult = resultOf as ResultOf.Failure

        assert(failureResult.throwable is NetworkException)
        val exception = failureResult.throwable as NetworkException

        assertEquals(502, exception.code)
        assertEquals("Bad Gateway", exception.message)
    }


    @Test
    fun `test get movies when server responses not found success`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = object : Dispatcher() {

            override fun dispatch(
                request: RecordedRequest
            ): MockResponse {
                return when (request.path) {
                    "/api/cinemaworld/movies" ->
                        UnitTestUtils.mockJsonResponse(200, "get_movies_by_provider_response_not_found_success.json")

                    "/api/filmworld/movies" ->
                        UnitTestUtils.mockJsonResponse(200, "get_movies_by_provider_response_not_found_success.json")

                    else ->
                        UnitTestUtils.mockJsonResponse(404)
                }
            }
        }

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Failure)
    }
}

