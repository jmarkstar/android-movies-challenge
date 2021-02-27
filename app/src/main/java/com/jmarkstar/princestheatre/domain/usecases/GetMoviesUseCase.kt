package com.jmarkstar.princestheatre.domain.usecases

import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.models.MovieModel

interface GetMoviesUseCase {

    suspend operator fun invoke(): ResultOf<List<MovieModel>>
}
