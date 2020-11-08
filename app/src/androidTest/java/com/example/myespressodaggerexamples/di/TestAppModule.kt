package com.example.myespressodaggerexamples.di

import android.app.Application
import com.bumptech.glide.Glide
import com.example.myespressodaggerexamples.util.FakeGlideRequestManager
import com.example.myespressodaggerexamples.util.GlideManager
import com.example.myespressodaggerexamples.util.JsonUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestAppModule {


    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(): GlideManager {
        return FakeGlideRequestManager()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideJsonUtil(application: Application): JsonUtil {
        return JsonUtil(application)
    }

}