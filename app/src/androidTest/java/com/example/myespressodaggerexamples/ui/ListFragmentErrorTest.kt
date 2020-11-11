package com.example.myespressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.util.Constants
import com.example.myespressodaggerexamples.util.EspressoIdlingResource
import com.example.myespressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ListFragmentErrorTest : BaseMainActivityTests() {

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun isErrorDialogShown_unknownError() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.SERVER_ERROR_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))
        onView(withSubstring(Constants.UNKNOWN_ERROR)).check(matches(isDisplayed()))

    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}