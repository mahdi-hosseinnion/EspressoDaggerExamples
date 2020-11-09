package com.example.myespressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.util.Constants
import com.example.myespressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ListFragmentIntegrationTest : BaseMainActivityTests() {

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun recyclerview_emptyData() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.EMPTY_LIST,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>()
        //actual test
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.no_data_textview)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    private fun getApp(): TestBaseApplication =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}