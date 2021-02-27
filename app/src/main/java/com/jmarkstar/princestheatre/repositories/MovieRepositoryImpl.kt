package com.jmarkstar.princestheatre.repositories

import com.jmarkstar.princestheatre.domain.repositories.MovieRepository
import com.jmarkstar.princestheatre.repositories.network.MoviesService
import com.jmarkstar.princestheatre.common.util.NetworkState
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.models.MovieModel
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val moviesService: MoviesService,
    val networkState: NetworkState
) : MovieRepository {

    override suspend fun getMovieDetail(movieName: String): ResultOf<MovieModel> {
        TODO("need implementation")
    }
}
