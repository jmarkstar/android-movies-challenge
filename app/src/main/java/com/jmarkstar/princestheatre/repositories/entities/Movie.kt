package com.jmarkstar.princestheatre.repositories.entities

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.jmarkstar.princestheatre.domain.models.MovieModel

@Entity(
    tableName = Movie.TABLE_NAME,
    indices = [Index("title")],
    primaryKeys = ["id"]
)

data class Movie(
    @SerializedName("ID") val id: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Poster") val poster: String,
    var providerId: String,
    @SerializedName("Year") val year: String? = null,
    @SerializedName("Rated") val rated: String? = null,
    @SerializedName("Released") val released: String? = null,
    @SerializedName("Runtime") val runTime: String? = null,
    @SerializedName("Genre") val genre: String? = null,
    @SerializedName("Director") val director: String? = null,
    @SerializedName("Writer") val writer: String? = null,
    @SerializedName("Actors") val actors: String? = null,
    @SerializedName("Plot") val plot: String? = null,
    @SerializedName("Language") val language: String? = null,
    @SerializedName("Country") val country: String? = null,
    @SerializedName("Production") val production: String? = null,
    @SerializedName("Price") val price: Double? = null
) {
    companion object {
        const val TABLE_NAME = "movies"
    }
}

fun Movie.toModel() = MovieModel(movieId = id, title = title, poster = poster)

fun List<Movie>.toModels() = map { it.toModel() }
