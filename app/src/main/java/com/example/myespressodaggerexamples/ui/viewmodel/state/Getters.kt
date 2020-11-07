package com.example.myespressodaggerexamples.ui.viewmodel.state

import android.os.Parcelable
import com.example.myespressodaggerexamples.models.BlogPost
import com.example.myespressodaggerexamples.models.Category
import com.example.myespressodaggerexamples.ui.viewmodel.MainViewModel
import com.example.myespressodaggerexamples.util.ErrorState
import com.example.myespressodaggerexamples.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getCurrentViewStateOrNew(): MainViewState {
    val value = viewState.value?.let{
        it
    }?: MainViewState()
    return value
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.areAnyJobsActive(): Boolean{
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.size > 0
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getNumActiveJobs(): Int {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.size
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.isJobAlreadyActive(stateEventName: String): Boolean {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.contains(stateEventName)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getLayoutManagerState(): Parcelable? {
    val viewState = getCurrentViewStateOrNew()
    return viewState.listFragmentView.layoutManagerState
}