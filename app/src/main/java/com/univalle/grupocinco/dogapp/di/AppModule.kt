package com.univalle.grupocinco.dogapp.di

import android.content.Context
import androidx.room.Room
import com.univalle.grupocinco.dogapp.data.dao.DogAppointmentDao
import com.univalle.grupocinco.dogapp.data.database.AppDatabase
import com.univalle.grupocinco.dogapp.network.DogApiService
import com.univalle.grupocinco.dogapp.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dog_appointments_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDogAppointmentDao(db: AppDatabase): DogAppointmentDao {
        return db.dogAppointmentDao()
    }
}