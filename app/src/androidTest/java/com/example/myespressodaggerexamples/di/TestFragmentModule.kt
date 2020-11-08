package com.example.myespressodaggerexamples.di

import android.app.Application
import androidx.fragment.app.FragmentFactory
import com.example.myespressodaggerexamples.fragments.FakeMainFragmentFactory
import com.example.myespressodaggerexamples.util.GlideManager
import com.example.myespressodaggerexamples.util.JsonUtil
import com.example.myespressodaggerexamples.viewmodels.FakeMainViewModelFactory
import com.example.myespressodaggerexamples.viewmodels.MainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@Module
object TestFragmentModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: FakeMainViewModelFactory,
        glideRequestManager: GlideManager

    ): FragmentFactory {
        return FakeMainFragmentFactory(
            viewModelFactory,
            glideRequestManager
        )
    }

}