package com.jmarkstar.princestheatre.domain.models

import com.jmarkstar.princestheatre.common.Constants

data class MovieModel(
    val movieId: String,
    val title: String,
    val poster: String,
    val summary: String = Constants.EMPTY,
    val actors: String = Constants.EMPTY,
    val genre: String = Constants.EMPTY,
    val prices: List<ProviderMoviePrice>? = null
) {
    override fun equals(other: Any?): Boolean = if (other is MovieModel) {
        title == other.title && poster == other.poster
    } else false

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + poster.hashCode()
        return result
    }
}
