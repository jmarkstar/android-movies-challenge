package com.jmarkstar.princestheatre.repositories.network.response

import com.google.gson.Gson
import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.common.UnitTestUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class GetMoviesByProviderResponseTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gson: Gson

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test parsing get movies by provider successfully`() {
        val jsonString = UnitTestUtils.readFileFromResources("get_movies_cinema_world_response_success.json")

        val response = gson.fromJson(jsonString, GetMoviesByProviderResponse::class.java)

        assertNotNull(response)
        assertEquals("Cinema World", response.providerName)
        assertEquals(11, response.movies.size)

        val firstMovie = response.movies[0]
        assertEquals("cw2488496", firstMovie.id)
        assertEquals("Star Wars: Episode VII - The Force Awakens", firstMovie.title)
        assertEquals("movie", firstMovie.type)
        assertEquals(
            "https://m.media-amazon.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SX300.jpg",
            firstMovie.poster
        )
    }
}
