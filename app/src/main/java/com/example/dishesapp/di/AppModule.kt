package com.example.dishesapp.di

import com.example.dishesapp.ApiService.DishApiService
import com.example.dishesapp.ApiService.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideApiService():DishApiService{
        return RetrofitInstance.api
    }
}