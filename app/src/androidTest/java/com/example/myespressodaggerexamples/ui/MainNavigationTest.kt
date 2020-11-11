package com.example.myespressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.models.BlogPost
import com.example.myespressodaggerexamples.util.Constants
import com.example.myespressodaggerexamples.util.EspressoIdlingResourceRule
import com.example.myespressodaggerexamples.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainNavigationTest : BaseMainActivityTests() {

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Test
    fun mainNavigationTest() {
        configureApiServiceAndRepositoryAndDaggerForTesting(
            blogPostJsonFileName = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesJsonFileName = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L
        )
        //setup activity
        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        val SELECTED_BLOG_PK = 8
        val blogListJson = jsonUtil.readJSONFromAsset(Constants.BLOG_POSTS_DATA_FILENAME)
        val blogList = Gson().fromJson<List<BlogPost>>(
            blogListJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        val SELECTED_BLOG_POST = blogList[SELECTED_BLOG_PK]

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(8)
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<BlogPostListAdapter.BlogPostViewHolder>(
                8,
                click()
            )
        )
        //check value been set
        onView(withId(R.id.blog_title)).check(matches(withText(SELECTED_BLOG_POST.title)))
        onView(withId(R.id.blog_body)).check(matches(withText(SELECTED_BLOG_POST.body)))
        onView(withId(R.id.blog_category)).check(matches(withText(SELECTED_BLOG_POST.category)))
        //nav to next
        onView(withId(R.id.blog_image)).perform(click())
        //check for image view
        onView(withId(R.id.scaling_image_view)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.blog_title)).check(matches(withText(SELECTED_BLOG_POST.title)))

        pressBack()

        recyclerView.check(matches(isDisplayed()))
        onView(withText(SELECTED_BLOG_POST.title)).check(matches(isDisplayed()))
    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}