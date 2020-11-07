package com.example.myespressodaggerexamples

import android.app.Application
import com.example.myespressodaggerexamples.di.AppComponent
import com.example.myespressodaggerexamples.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
open class BaseApplication: Application() {

    private val TAG: String = "AppDebug"

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    open fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}