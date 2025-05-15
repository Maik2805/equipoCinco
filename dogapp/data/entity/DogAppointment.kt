package com.univalle.grupocinco.dogapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dog_appointments")
data class DogAppointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dogName: String,
    val breed: String,
    val ownerName: String,
    val phone: Long,
    val symptom: String
): Serializable