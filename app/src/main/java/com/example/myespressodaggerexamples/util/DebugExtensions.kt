package com.example.myespressodaggerexamples.util

import android.util.Log
import com.example.myespressodaggerexamples.util.Constants.DEBUG
import com.example.myespressodaggerexamples.util.Constants.TAG

fun printLogD(className: String?, message: String ) {
    if (DEBUG) {
        Log.d(TAG, "$className: $message")
    }
}