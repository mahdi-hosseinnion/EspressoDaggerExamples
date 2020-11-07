package com.example.myespressodaggerexamples.di

import com.example.myespressodaggerexamples.api.ApiService
import com.example.myespressodaggerexamples.repository.MainRepository
import com.example.myespressodaggerexamples.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object RepositoryModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }


    @JvmStatic
    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepositoryImpl(apiService)
    }
}