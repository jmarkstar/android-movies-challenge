package com.jmarkstar.princestheatre.repositories.local.daos

import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.repositories.local.PrepopulateCallback
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
class ProviderDaoTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var providerDao: ProviderDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test provider count`() = runBlocking {
        assertEquals(PrepopulateCallback.fakeProviders.size, providerDao.count())
    }

    @Test
    fun `test provider list`() = runBlocking {
        assert(PrepopulateCallback.fakeProviders.containsAll(providerDao.getAll()))
    }
}
