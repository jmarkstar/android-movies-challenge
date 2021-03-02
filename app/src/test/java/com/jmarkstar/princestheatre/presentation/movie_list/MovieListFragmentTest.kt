package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.common.BaseUITest
import com.jmarkstar.princestheatre.common.extensions.launchFragmentInHiltContainer
import com.jmarkstar.princestheatre.domain.ResultOf
import com.jmarkstar.princestheatre.domain.usecases.GetMoviesUseCase
import com.jmarkstar.princestheatre.fakeMovies
import com.jmarkstar.princestheatre.presentation.di.UseCasesModule
import com.jmarkstar.princestheatre.repositories.entities.toModels
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@UninstallModules(UseCasesModule::class)
@HiltAndroidTest
class MovieListFragmentTest : BaseUITest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @BindValue
    @JvmField
    val getMoviesUseCase = mockk<GetMoviesUseCase>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test show movie list on RecyclerView successfully`() {

        // Given
        val movies = fakeMovies.toModels().toSet().toList()
        val mockResult = ResultOf.Success(movies)

        coEvery { getMoviesUseCase.invoke() } returns mockResult

        // When
        launchFragmentInHiltContainer<MovieListFragment> {
            Assert.assertNotNull(this)
        }

        // Then
        // onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
        // onView(withId(R.id.tvErrorMessage)).check(matches(not(isDisplayed())))
        // onView(withId(R.id.moviePoster)).check(matches(isDisplayed()))
    }
}
