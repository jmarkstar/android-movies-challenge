package com.jmarkstar.princestheatre.domain.repositories

import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.models.MovieModel

interface MovieRepository {

    suspend fun getMovieDetail(movieName: String): ResultOf<MovieModel>
}
