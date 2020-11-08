package com.example.myespressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myespressodaggerexamples.R
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.api.FakeApiService
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.fragments.FakeMainFragmentFactory
import com.example.myespressodaggerexamples.models.BlogPost
import com.example.myespressodaggerexamples.repository.FakeMainRepositoryImpl
import com.example.myespressodaggerexamples.ui.viewmodel.state.setSelectedBlogPost
import com.example.myespressodaggerexamples.util.Constants
import com.example.myespressodaggerexamples.util.FakeGlideRequestManager
import com.example.myespressodaggerexamples.util.JsonUtil
import com.example.myespressodaggerexamples.viewmodels.FakeMainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailFragmentTest {


    @Inject
    lateinit var fakeMainViewModelFactory: FakeMainViewModelFactory

    @Inject
    lateinit var fragmentFactory: FakeMainFragmentFactory

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Inject
    lateinit var fakeGlideRequestManager: FakeGlideRequestManager

    val uiCommunicationListener: UICommunicationListener = mockk<UICommunicationListener>()

    @Before
    fun init() {
        every { uiCommunicationListener.showStatusBar() } just runs
        every { uiCommunicationListener.expandAppBar() } just runs
        every { uiCommunicationListener.hideCategoriesMenu() } just runs
    }

    @Test
    fun is_selectedBlogPostDetailSet() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication
        val apiService = configureFakeApiService(
            Constants.BLOG_POSTS_DATA_FILENAME,
            Constants.CATEGORIES_DATA_FILENAME,
            0L,
            app
        )
        configureFakeRepository(
            apiService,
            app
        )

        injectTest(app)

        fragmentFactory.uiCommunicationListener = uiCommunicationListener

        val scenario = launchFragmentInContainer<DetailFragment>(
            factory = fragmentFactory
        )

        val blogListJson = jsonUtil.readJSONFromAsset(Constants.BLOG_POSTS_DATA_FILENAME)
        val blogList = Gson().fromJson<List<BlogPost>>(
            blogListJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )

        val selectedBlogPost = blogList[0]

        scenario.onFragment { fragment ->
            fragment.viewModel.setSelectedBlogPost(
                selectedBlogPost
            )
        }

        onView(withId(R.id.blog_title)).check(matches(withText(selectedBlogPost.title)))
        onView(withId(R.id.blog_category)).check(matches(withText(selectedBlogPost.category)))
        onView(withId(R.id.blog_body)).check(matches(withText(selectedBlogPost.body)))

    }

    fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

    fun configureFakeApiService(
        blogPostJsonFileName: String?,
        categoriesJsonFileName: String?,
        networkDelay: Long?,
        application: TestBaseApplication
    ): FakeApiService {
        val fakeApiService = (application.appComponent as TestAppComponent).apiService
        blogPostJsonFileName?.let { fakeApiService.blogPostJsonFileName = it }
        categoriesJsonFileName?.let { fakeApiService.categoriesJsonFileName = it }
        networkDelay?.let { fakeApiService.networkDelay = it }
        return fakeApiService
    }

    fun configureFakeRepository(
        apiService: FakeApiService,
        application: TestBaseApplication
    ): FakeMainRepositoryImpl {
        val mainRepository = (application.appComponent as TestAppComponent).mainRepository
        mainRepository.apiService = apiService
        return mainRepository
    }
}