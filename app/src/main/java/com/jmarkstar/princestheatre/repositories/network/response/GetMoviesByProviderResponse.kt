package com.jmarkstar.princestheatre.repositories.network.response

import com.google.gson.annotations.SerializedName
import com.jmarkstar.princestheatre.repositories.entities.Movie

data class GetMoviesByProviderResponse(
    @SerializedName("Provider") val providerName: String,
    @SerializedName("Movies") val movies: List<Movie>
)
