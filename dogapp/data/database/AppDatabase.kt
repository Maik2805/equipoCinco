package com.univalle.grupocinco.dogapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.univalle.grupocinco.dogapp.data.dao.DogAppointmentDao
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment

@Database(entities = [DogAppointment::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dogAppointmentDao(): DogAppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dog_appointments_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}