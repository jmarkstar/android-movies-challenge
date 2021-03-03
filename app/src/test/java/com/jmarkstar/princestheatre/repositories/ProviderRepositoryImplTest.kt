package com.jmarkstar.princestheatre.repositories

import com.google.gson.Gson
import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.common.UnitTestUtils
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import com.jmarkstar.princestheatre.fakeMovies
import com.jmarkstar.princestheatre.repositories.exceptions.NetworkException
import com.jmarkstar.princestheatre.repositories.local.daos.MovieDao
import com.jmarkstar.princestheatre.repositories.local.daos.ProviderDao
import com.jmarkstar.princestheatre.repositories.network.MoviesService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class ProviderRepositoryImplTest : BaseTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

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

    @Before
    fun setUp() {
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
    fun `test get movies Repository when server responses successfully`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = MockServerDispatchers.providerAllSuccessResponses

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Success)

        val movieModels = (resultOf as ResultOf.Success).value
        assertEquals(11, movieModels.size)
    }

    @Test
    fun `test get movies Repository when there is not internet connection`() = runBlocking {
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
    fun `test get movies Repository when an server error occurs in one provider endpoint`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = MockServerDispatchers.providerOneFailedResponse

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Success)

        val movieModels = (resultOf as ResultOf.Success).value
        assertEquals(11, movieModels.size)
    }

    @Test
    fun `test get movies Repository when an server error occurs in all provider endpoints`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = MockServerDispatchers.providerAllFailedResponses

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
    fun `test get movies Repository when server responses not found success`() = runBlocking {

        // Given
        networkState.isConnected = true
        mockWebServer.dispatcher = MockServerDispatchers.providerAllNotFoundResponses

        // When
        val resultOf = providerRepository.getMovies()

        // Then
        assert(resultOf is ResultOf.Failure)
    }
}
