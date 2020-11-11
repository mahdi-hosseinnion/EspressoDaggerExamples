package com.example.myespressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.ui.viewmodel.state.MainStateEvent
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
        configureApiServiceAndRepositoryAndDaggerForTesting(
            blogPostJsonFileName = Constants.SERVER_ERROR_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0
        )

        //setup activity
        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))
        onView(withSubstring(Constants.UNKNOWN_ERROR)).check(matches(isDisplayed()))

    }
    @Test
    fun isErrorDialogShown_timeOutError() {
        //setup
        configureApiServiceAndRepositoryAndDaggerForTesting(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 4000,
        )
        //setup activity
        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))
        onView(withSubstring(Constants.NETWORK_ERROR_TIMEOUT)).check(matches(isDisplayed()))

    }
    @Test
    fun isErrorDialogShown_emptyBlog() {
        //setup
        configureApiServiceAndRepositoryAndDaggerForTesting(
            blogPostJsonFileName = Constants.SERVER_ERROR_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0,
        )
        //setup activity
        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))
        onView(withSubstring(MainStateEvent.GetAllBlogs().errorInfo())).check(matches(isDisplayed()))

    }
    @Test
    fun isErrorDialogShown_emptyCategory() {
        //setup
        configureApiServiceAndRepositoryAndDaggerForTesting(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.SERVER_ERROR_FILENAME,
            networkDelay = 0,
        )
        //setup activity
        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))
        onView(withSubstring(MainStateEvent.GetCategories().errorInfo())).check(matches(isDisplayed()))

    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}