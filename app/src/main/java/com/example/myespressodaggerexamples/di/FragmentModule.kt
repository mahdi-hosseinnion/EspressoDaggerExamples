package com.example.myespressodaggerexamples.di

import androidx.fragment.app.FragmentFactory
import com.example.myespressodaggerexamples.fragments.MainFragmentFactory
import com.example.myespressodaggerexamples.util.GlideManager
import com.example.myespressodaggerexamples.viewmodels.MainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object FragmentModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: MainViewModelFactory,
        glideManager: GlideManager
    ): FragmentFactory {
        return MainFragmentFactory(viewModelFactory, glideManager)
    }

}

