package com.example.myespressodaggerexamples.di

import android.app.Application
import com.bumptech.glide.Glide
import com.example.myespressodaggerexamples.util.GlideManager
import com.example.myespressodaggerexamples.util.GlideRequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application
    ): GlideManager {
        return GlideRequestManager(
            Glide.with(application)
        )
    }



}
