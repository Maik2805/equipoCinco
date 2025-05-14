package com.univalle.grupocinco.dogapp.repository

import com.univalle.grupocinco.dogapp.data.dao.DogAppointmentDao
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment
import com.univalle.grupocinco.dogapp.network.DogApiService
import jakarta.inject.Inject

class DogRepository @Inject constructor(
    private val apiService: DogApiService,
    private val dogAppointmentDao: DogAppointmentDao
) {

    suspend fun getAllBreeds(): List<String> {
        val response = apiService.getBreeds()
        if (response.isSuccessful) {
            val breedsMap = response.body()?.message ?: emptyMap()
            val breedList = mutableListOf<String>()

            for ((breed, subBreeds) in breedsMap) {
                if (subBreeds.isEmpty()) {
                    breedList.add(breed.replaceFirstChar { it.lowercase() })
                } else {
                    subBreeds.forEach { subBreed ->
                        breedList.add("${breed.replaceFirstChar { it.lowercase() }} ${subBreed.replaceFirstChar { it.lowercase() }}")
                    }
                }
            }
            println(breedList)
            return breedList.sorted()
        } else {
            throw Exception("Error en la respuesta del servidor")
        }
    }

    suspend fun getBreedImage(breed: String): String? {
        try {
            val (mainBreed, subBreed) = parseBreedComponents(breed)

            val response = subBreed?.let {
                apiService.getRandomDogImageByBreedAndSubBreed(mainBreed, subBreed)
            } ?: apiService.getRandomDogImageByBreed(mainBreed)

            return response.body()?.message ?: throw Exception("Error al consumir api")

        }  catch (e: Exception) {
            println("No se pudo bucar la imagen")
            e.printStackTrace()
        }
        return null
    }

    private fun parseBreedComponents(breed: String): Pair<String, String?> {
        val breedParts = breed.split(" ")
        return when (breedParts.size) {
            1 -> breedParts[0] to null
            else -> breedParts[0] to breedParts[1]
        }
    }

    suspend fun insertAppointment(appointment: DogAppointment) {
        dogAppointmentDao.insert(appointment)
    }

    suspend fun getAllAppointments(): List<DogAppointment> {
        return dogAppointmentDao.getAll()
    }

    suspend fun deleteAppointment(appointment: DogAppointment) {
        dogAppointmentDao.delete(appointment)
    }

    suspend fun deleteAllAppointments() {
        dogAppointmentDao.deleteAll()
    }
}
