package com.jmarkstar.princestheatre.repositories.local.daos

import com.jmarkstar.princestheatre.common.BaseTest
import com.jmarkstar.princestheatre.di.ApplicationModule
import com.jmarkstar.princestheatre.repositories.di.LocalModule
import com.jmarkstar.princestheatre.repositories.local.PrepopulateCallback
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(LocalModule::class, ApplicationModule::class)
@ExperimentalCoroutinesApi
class ProviderDaoTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var providerDao: ProviderDao

    override fun setUp() {
        super.setUp()
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
