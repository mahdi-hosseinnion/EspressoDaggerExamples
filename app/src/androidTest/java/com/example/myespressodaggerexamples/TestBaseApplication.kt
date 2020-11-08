package com.example.myespressodaggerexamples

import android.app.Application
import com.example.myespressodaggerexamples.di.DaggerAppComponent
import com.example.myespressodaggerexamples.di.DaggerTestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TestBaseApplication : BaseApplication() {

    override fun initAppComponent() {
        appComponent = DaggerTestAppComponent.builder()
            .application(this).build()
    }
}