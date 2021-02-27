package com.jmarkstar.princestheatre.repositories

import com.google.gson.Gson
import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import com.jmarkstar.princestheatre.repositories.local.daos.MovieDao
import com.jmarkstar.princestheatre.repositories.network.MoviesService
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.models.MovieModel
import com.jmarkstar.princestheatre.repositories.entities.toModel
import com.jmarkstar.princestheatre.repositories.entities.toModels
import com.jmarkstar.princestheatre.repositories.exceptions.NetworkException
import com.jmarkstar.princestheatre.repositories.local.daos.ProviderDao
import com.jmarkstar.princestheatre.repositories.network.response.GenericApiError
import com.jmarkstar.princestheatre.repositories.network.response.GetMoviesByProviderResponse
import com.jmarkstar.princestheatre.repositories.network.response.NotFoundErrorResponse
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val providerDao: ProviderDao,
    private val moviesService: MoviesService,
    private val networkState: NetworkState,
    private val gson: Gson
) : ProviderRepository {

    override suspend fun getMovies(): ResultOf<List<MovieModel>> = if (networkState.isConnected) {

        var isSuccessful = true
        val movieModels = HashSet<MovieModel>()
        var exception = Exception()

        movieDao.deleteAll()
        providerDao.getAll().forEach { provider ->
            val response = moviesService.getMoviesBy(provider.id)
            if (response.isSuccessful && response.body() != null) {

                // API is responding another json structure when the provider is not found.
                response.body()?.string()?.let { stringBody ->
                    try {
                        val moviesResponse = gson.fromJson(stringBody, GetMoviesByProviderResponse::class.java)
                        val entities = moviesResponse.movies.map {
                            it.providerId = provider.id
                            movieModels.add(it.toModel())
                            it
                        }
                        movieDao.insertAll(entities)
                    } catch (ex: Exception) {
                        isSuccessful = false
                        val notFoundResponse = gson.fromJson(stringBody, NotFoundErrorResponse::class.java)
                        exception = NetworkException(notFoundResponse.statusCode, notFoundResponse.body.message)
                    }
                }
            } else {
                isSuccessful = false
                exception = NetworkException(
                    response.code(),
                    response.errorBody()?.string()?.let {
                        gson.fromJson(it, GenericApiError::class.java).message
                    }
                )
                Timber.e(exception)
            }
        }

        if (!isSuccessful && movieModels.isEmpty()) ResultOf.Failure(exception) else ResultOf.Success(movieModels.toList())
    } else {
        ResultOf.Success(movieDao.getAll().toModels().toSet().toList())
    }
}
