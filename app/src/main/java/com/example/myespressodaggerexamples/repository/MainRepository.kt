package com.example.myespressodaggerexamples.repository

import com.example.myespressodaggerexamples.ui.viewmodel.state.MainViewState
import com.example.myespressodaggerexamples.util.DataState
import com.example.myespressodaggerexamples.util.StateEvent
import kotlinx.coroutines.flow.Flow

interface MainRepository{

    fun getBlogs(stateEvent: StateEvent, category: String): Flow<DataState<MainViewState>>

    fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>>

    fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>>
}