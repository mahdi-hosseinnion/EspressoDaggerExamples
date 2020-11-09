package com.example.myespressodaggerexamples.ui

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.ui.viewmodel.state.MainViewState
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

    // if query for categories returns an empty list, the user should still see
    // the menu item "All"
    @Test
    fun isCategoryListEmpty() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.EMPTY_LIST,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>().onActivity { mainActivity ->
            val toolBar: Toolbar = mainActivity.findViewById(R.id.tool_bar)
            mainActivity.viewModel.viewState.observe(mainActivity, Observer { viewState ->
                if (viewState.activeJobCounter.size == 0) {
                    toolBar.showOverflowMenu()
                }
            })
        }
        onView(withSubstring("fun")).check(doesNotExist())
        onView(withSubstring("dogs")).check(doesNotExist())
        onView(withSubstring("earthporn")).check(doesNotExist())
        onView(withSubstring("All")).check(matches(isDisplayed()))
    }

    @Test
    fun testListData_isScrolling() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.EMPTY_LIST,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(8)
        )

        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(5)
        )

        onView(withText("Mountains in Washington")).check(matches(isDisplayed()))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(0)
        )

        onView(withText("Vancouver PNE 2019")).check(matches(isDisplayed()))

        onView(withId(R.id.no_data_textview)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testListData_isScrolling_earthpornCategory() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>().onActivity { mainActivity ->
            val toolbar: Toolbar = mainActivity.findViewById(R.id.tool_bar)
            mainActivity.viewModel.viewState.observe(mainActivity,
                object : Observer<MainViewState> {
                    override fun onChanged(t: MainViewState?) {
                        if (t?.activeJobCounter?.size == 0) {
                            toolbar.showOverflowMenu()
                            mainActivity.viewModel.viewState.removeObserver(this)
                        }

                    }

                })
        }
        // click "earthporn" category from menu
        val CATEGORY_NAME = "earthporn"
        onView(withText(CATEGORY_NAME)).perform(click())

        onView(withText("Mountains in Washington")).check(matches(isDisplayed()))

        onView(withText("France Mountain Range")).check(matches(isDisplayed()))

        onView(withText("Vancouver PNE 2019")).check(doesNotExist())
    }

    @Test
    fun testListData_isScrolling_funCategory() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>().onActivity { mainActivity ->
            val toolbar: Toolbar = mainActivity.findViewById(R.id.tool_bar)
            mainActivity.viewModel.viewState.observe(mainActivity,
                object : Observer<MainViewState> {
                    override fun onChanged(t: MainViewState?) {
                        if (t?.activeJobCounter?.size == 0) {
                            toolbar.showOverflowMenu()
                            mainActivity.viewModel.viewState.removeObserver(this)
                        }

                    }

                })
        }
        // click "earthporn" category from menu
        val CATEGORY_NAME = "fun"
        onView(withText(CATEGORY_NAME)).perform(click())

        onView(withText("Vancouver PNE 2019")).check(matches(isDisplayed()))

        onView(withText("My Brother Blake")).check(matches(isDisplayed()))

        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

        onView(withText("Aldergrove Park")).check(doesNotExist())
    }

    @Test
    fun isInstanceStateSavedAndRestored_OnDestroyActivity() {
        //setup
        val app = getApp()
        val apiService = configureFakeApiService(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0,
            application = app
        )
        configureFakeRepository(apiService, app)
        injectTest(app)
        //setup activity
        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(8)
        )

        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

        scenario.recreate()

        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))


    }

    private fun getApp(): TestBaseApplication =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}