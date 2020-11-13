package com.example.myespressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.fragments.FakeMainFragmentFactory
import com.example.myespressodaggerexamples.fragments.MainFragmentFactory
import com.example.myespressodaggerexamples.util.Constants
import com.example.myespressodaggerexamples.util.EspressoIdlingResource
import com.example.myespressodaggerexamples.util.EspressoIdlingResourceRule
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ListFragmentNavigationTest : BaseMainActivityTests() {

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var fragmentFactory: FakeMainFragmentFactory

    val uiCommunicationListener = mockk<UICommunicationListener>()

    @Before
    fun init() {
        every { uiCommunicationListener.showStatusBar() } just runs
        every { uiCommunicationListener.expandAppBar() } just runs
        every { uiCommunicationListener.displayMainProgressBar(any()) } just runs
        every { uiCommunicationListener.hideCategoriesMenu() } just runs
        every { uiCommunicationListener.showCategoriesMenu(any()) } just runs
    }

    @Test
    fun testNavigationToDetailFragment() {
        configureApiServiceAndRepositoryAndDaggerForTesting(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L
        )

        fragmentFactory.uiCommunicationListener = uiCommunicationListener
        val navController = TestNavHostController(getApp())
        navController.setGraph(R.navigation.main_nav_graph)
        navController.setCurrentDestination(R.id.listFragment)

        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        val recyclerView = onView(withId(R.id.recycler_view))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(7)
        )

        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<BlogPostListAdapter.BlogPostViewHolder>(7,click())
        )

        assertEquals(navController.currentDestination?.id,R.id.detailFragment)


    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}