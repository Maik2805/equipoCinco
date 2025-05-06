package com.univalle.grupocinco.dogapp.di

import com.univalle.grupocinco.dogapp.network.DogApiService
import com.univalle.grupocinco.dogapp.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDogApiService(): DogApiService {
        return RetrofitClient.apiService
    }
}