package com.univalle.grupocinco.dogapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment

@Dao
interface DogAppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: DogAppointment)

    @Query("SELECT * FROM dog_appointments")
    suspend fun getAll(): List<DogAppointment>

    @Delete
    suspend fun delete(appointment: DogAppointment)

    @Query("DELETE FROM dog_appointments")
    suspend fun deleteAll()
}