package com.example.myespressodaggerexamples.ui

import androidx.test.platform.app.InstrumentationRegistry
import com.example.myespressodaggerexamples.TestBaseApplication
import com.example.myespressodaggerexamples.api.FakeApiService
import com.example.myespressodaggerexamples.di.TestAppComponent
import com.example.myespressodaggerexamples.repository.FakeMainRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseMainActivityTests {
    abstract fun injectTest(application: TestBaseApplication)

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
    fun getApp(): TestBaseApplication =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

}