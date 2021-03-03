package com.jmarkstar.princestheatre.presentation.movie_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.jmarkstar.princestheatre.R
import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.common.TestCoroutineRule
import com.jmarkstar.princestheatre.common.extensions.launchFragmentInHiltContainer
import com.jmarkstar.princestheatre.di.TestModule
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
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
@UninstallModules(UseCasesModule::class, TestModule::class)
@HiltAndroidTest
class MovieListFragmentTest : BaseTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @BindValue
    @JvmField
    val getMoviesUseCase = mockk<GetMoviesUseCase>()

    @BindValue
    @JvmField
    val coroutineDispatcher = coroutineRule.testCoroutineDispatcher

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test ProgressBar when Fragment is waiting for response`() {

        // Given
        val movies = fakeMovies.toModels().toSet().toList()
        val mockResult = ResultOf.Success(movies)
        coEvery { getMoviesUseCase.invoke() } returns mockResult

        // When
        coroutineDispatcher.pauseDispatcher()
        launchFragmentInHiltContainer<MovieListFragment>()

        // Then
        onView(withId(R.id.pgLoading)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovies)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvErrorMessage)).check(matches(not(isDisplayed())))

        coroutineDispatcher.resumeDispatcher()
        coVerify { getMoviesUseCase.invoke() }

        onView(withId(R.id.rvMovies)).check(matches((isDisplayed())))
        onView(withId(R.id.pgLoading)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvErrorMessage)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `test Navigation when User clicks on RecyclerView item`() {

        // Given
        val movies = fakeMovies.toModels().toSet().toList()
        val mockResult = ResultOf.Success(movies)

        coEvery { getMoviesUseCase.invoke() } returns mockResult

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_movies_graph)
        navController.setCurrentDestination(R.id.movieListFragment)

        // When
        launchFragmentInHiltContainer<MovieListFragment> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }

        // Then
        coVerify { getMoviesUseCase.invoke() }

        onView(withId(R.id.rvMovies)).perform(actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(0, click()))

        assertEquals(R.id.movieDetailsFragment, navController.currentDestination?.id)
    }
}
