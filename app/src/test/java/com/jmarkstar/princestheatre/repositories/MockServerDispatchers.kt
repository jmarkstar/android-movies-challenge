package com.jmarkstar.princestheatre.repositories

import com.jmarkstar.princestheatre.common.UnitTestUtils
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object MockServerDispatchers {

    val providerAllSuccessResponses = object : Dispatcher() {

        override fun dispatch(
            request: RecordedRequest
        ): MockResponse {
            return when (request.path) {
                "/api/cinemaworld/movies" ->
                    UnitTestUtils.mockJsonResponse(200, "get_movies_cinema_world_response_success.json")

                "/api/filmworld/movies" ->
                    UnitTestUtils.mockJsonResponse(200, "get_movies_film_world_response_success.json")

                else ->
                    UnitTestUtils.mockJsonResponse(404, "get_movies_by_provider_response_not_found_success.json")
            }
        }
    }

    val providerOneFailedResponse = object : Dispatcher() {

        override fun dispatch(
            request: RecordedRequest
        ): MockResponse {
            return when (request.path) {
                "/api/cinemaworld/movies" ->
                    UnitTestUtils.mockJsonResponse(200, "get_movies_cinema_world_response_success.json")

                "/api/filmworld/movies" ->
                    UnitTestUtils.mockJsonResponse(502, "get_movies_by_provider_response_502_failed.json")

                else ->
                    UnitTestUtils.mockJsonResponse(404, "get_movies_by_provider_response_not_found_success.json")
            }
        }
    }

    val providerAllFailedResponses = object : Dispatcher() {

        override fun dispatch(
            request: RecordedRequest
        ): MockResponse {
            return when (request.path) {
                "/api/cinemaworld/movies" ->
                    UnitTestUtils.mockJsonResponse(502, "get_movies_by_provider_response_502_failed.json")

                "/api/filmworld/movies" ->
                    UnitTestUtils.mockJsonResponse(502, "get_movies_by_provider_response_502_failed.json")

                else ->
                    UnitTestUtils.mockJsonResponse(404, "get_movies_by_provider_response_not_found_success.json")
            }
        }
    }

    val providerAllNotFoundResponses = object : Dispatcher() {

        override fun dispatch(
            request: RecordedRequest
        ): MockResponse {
            return when (request.path) {
                "/api/cinemaworld/movies" ->
                    UnitTestUtils.mockJsonResponse(200, "get_movies_by_provider_response_not_found_success.json")

                "/api/filmworld/movies" ->
                    UnitTestUtils.mockJsonResponse(200, "get_movies_by_provider_response_not_found_success.json")

                else ->
                    UnitTestUtils.mockJsonResponse(404)
            }
        }
    }
}
