package com.example.myespressodaggerexamples.util

import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val TAG = "EspressoIdlingResource"
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)
    fun increment() {
        countingIdlingResource.increment()
        Log.d(TAG, "increment: INCREMENT")
    }

    fun decrement() {
        countingIdlingResource.decrement()
        Log.d(TAG, "decrement: DECREMENT")
    }
    fun clear(): Boolean {
        return if (!countingIdlingResource.isIdleNow) {
            decrement()
            false
        } else{
            true
        }
    }
}
