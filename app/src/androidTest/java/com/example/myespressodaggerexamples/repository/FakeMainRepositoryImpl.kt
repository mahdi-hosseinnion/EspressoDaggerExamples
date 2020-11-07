package com.example.myespressodaggerexamples.repository

import com.example.myespressodaggerexamples.api.ApiService
import com.example.myespressodaggerexamples.models.BlogPost
import com.example.myespressodaggerexamples.models.Category
import com.example.myespressodaggerexamples.ui.viewmodel.state.MainViewState
import com.example.myespressodaggerexamples.util.ApiResponseHandler
import com.example.myespressodaggerexamples.util.DataState
import com.example.myespressodaggerexamples.util.StateEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMainRepositoryImpl
@Inject
constructor()
    : MainRepository {

    lateinit var apiService: ApiService

    private val CLASS_NAME = "MainRepositoryImpl"

    @Throws(UninitializedPropertyAccessException::class)
    override fun getBlogs(stateEvent: StateEvent, category: String): Flow<DataState<MainViewState>> {
        throwExceptionIfApiServiceNotInitialized()
        return flow {

            val response = safeApiCall(Dispatchers.IO) { apiService.getBlogPosts(category) }

            emit(
                    object : ApiResponseHandler<MainViewState, List<BlogPost>>(
                            response = response,
                            stateEvent = stateEvent
                    ) {
                        override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                            return DataState.data(
                                    data = MainViewState(
                                            listFragmentView = MainViewState.ListFragmentView(
                                                    blogs = resultObj
                                            )
                                    ),
                                    stateEvent = stateEvent
                            )
                        }

                    }.result
            )
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        throwExceptionIfApiServiceNotInitialized()
        return flow {

            val response = safeApiCall(Dispatchers.IO) { apiService.getAllBlogPosts() }

            emit(
                    object : ApiResponseHandler<MainViewState, List<BlogPost>>(
                            response = response,
                            stateEvent = stateEvent
                    ) {
                        override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                            return DataState.data(
                                    data = MainViewState(
                                            listFragmentView = MainViewState.ListFragmentView(
                                                    blogs = resultObj
                                            )
                                    ),
                                    stateEvent = stateEvent
                            )
                        }

                    }.result
            )
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        throwExceptionIfApiServiceNotInitialized()
        return flow {

            val response = safeApiCall(Dispatchers.IO) { apiService.getCategories() }

            emit(
                    object : ApiResponseHandler<MainViewState, List<Category>>(
                            response = response,
                            stateEvent = stateEvent
                    ) {
                        override fun handleSuccess(resultObj: List<Category>): DataState<MainViewState> {
                            return DataState.data(
                                    data = MainViewState(
                                            listFragmentView = MainViewState.ListFragmentView(
                                                    categories = resultObj
                                            )
                                    ),
                                    stateEvent = stateEvent
                            )
                        }

                    }.result
            )
        }
    }

    fun throwExceptionIfApiServiceNotInitialized() {
        if (!::apiService.isInitialized) {
            throw UninitializedPropertyAccessException(
                    "Did you forget to set the ApiService in FakeMainRepositoryImpl?"
            )
        }
    }

}

