package com.jmarkstar.princestheatre.domain.repositories

import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.models.MovieModel

interface ProviderRepository {

    suspend fun getMovies(): ResultOf<List<MovieModel>>
}
