package com.jmarkstar.princestheatre.repositories.network

import com.jmarkstar.princestheatre.repositories.entities.Movie
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("api/{providerId}/movies")
    suspend fun getMoviesBy(
        @Path("providerId") providerId: String
    ): Response<ResponseBody>

    @GET("api/{providerId}/movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("providerId") providerId: String,
        @Path("movieId") movieId: String
    ): Response<Movie>
}
