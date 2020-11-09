package com.example.myespressodaggerexamples.di

import android.app.Application
import com.example.myespressodaggerexamples.api.FakeApiService
import com.example.myespressodaggerexamples.repository.FakeMainRepositoryImpl
import com.example.myespressodaggerexamples.ui.DetailFragment
import com.example.myespressodaggerexamples.ui.DetailFragmentTest
import com.example.myespressodaggerexamples.ui.ListFragmentIntegrationTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(
    modules = [
        TestAppModule::class,
        TestFragmentModule::class,
        TestViewModelModule::class
    ]
)
interface TestAppComponent : AppComponent {

    val apiService: FakeApiService

    val mainRepository: FakeMainRepositoryImpl

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(detailFragmentTest: DetailFragmentTest)
    fun inject(listFragmentIntegrationTest: ListFragmentIntegrationTest)
}
