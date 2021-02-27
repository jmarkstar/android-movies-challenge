package com.jmarkstar.princestheatre.domain.usecases

import com.jmarkstar.princestheatre.domain.repositories.ProviderRepository
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val providerRepository: ProviderRepository
) : GetMoviesUseCase {

    override suspend fun invoke() = providerRepository.getMovies()
}
