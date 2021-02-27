package com.jmarkstar.princestheatre.repositories.entities

import com.google.gson.Gson
import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.common.UnitTestUtils
import com.jmarkstar.princestheatre.di.ApplicationModule
import com.jmarkstar.princestheatre.repositories.di.LocalModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(LocalModule::class, ApplicationModule::class)
@ExperimentalCoroutinesApi
class MovieTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gSon: Gson

    override fun setUp() {
        super.setUp()
        hiltRule.inject()
    }

    @Test
    fun `test parsing movie details successfully`() {
        val jsonString = UnitTestUtils.readFileFromResources("get_movie_detail_response_success.json")

        val response = gSon.fromJson(jsonString, Movie::class.java)

        assertEquals("cw2527336", response.id)
        assertEquals("Star Wars: Episode VIII - The Last Jedi", response.title)
        assertEquals("2017", response.year)
        assertEquals("PG-13", response.rated)
        assertEquals("15 Dec 2017", response.released)
        assertEquals("152 min", response.runTime)
        assertEquals("Action, Adventure, Fantasy, Sci-Fi", response.genre)
        assertEquals("Rian Johnson", response.director)
        assertEquals("Rian Johnson, George Lucas (based on characters created by)", response.writer)
        assertEquals("Mark Hamill, Carrie Fisher, Adam Driver, Daisy Ridley", response.actors)
        assertEquals(
            "Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares for battle with the First Order.",
            response.plot
        )
        assertEquals("English", response.language)
        assertEquals("USA", response.country)
        assertEquals("Walt Disney Pictures", response.production)
        assertEquals("movie", response.type)
        assertEquals(
            "https://m.media-amazon.com/images/M/MV5BMjQ1MzcxNjg4N15BMl5BanBnXkFtZTgwNzgwMjY4MzI@._V1_SX300.jpg",
            response.poster
        )
        response.price?.apply {
            assertEquals(24.0, this, 0.0)
        }
    }
}
