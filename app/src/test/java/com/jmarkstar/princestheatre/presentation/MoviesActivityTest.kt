package com.jmarkstar.princestheatre.presentation

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jmarkstar.princestheatre.common.BaseUITest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertNotNull

@HiltAndroidTest
class MoviesActivityTest : BaseUITest() {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityScenarioRule = ActivityScenarioRule(MoviesActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun `test show movie list on RecyclerView successfully`() {

        activityScenarioRule.scenario.onActivity {
            assertNotNull(it)
            it.binding.apply {
                // TODO: Will be implemented
            }
        }
    }
}
