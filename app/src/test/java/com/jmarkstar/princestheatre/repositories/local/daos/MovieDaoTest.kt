package com.jmarkstar.princestheatre.repositories.local.daos

import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.fakeMovies
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class MovieDaoTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test movies dao cases`() = runBlocking {

        assertEquals(0, movieDao.count())

        movieDao.insertAll(fakeMovies)

        assertEquals(6, movieDao.count())

        val movies = movieDao.getAll()

        assert(fakeMovies.containsAll(movies))

        movieDao.deleteAll()

        assertEquals(0, movieDao.count())
    }
}

