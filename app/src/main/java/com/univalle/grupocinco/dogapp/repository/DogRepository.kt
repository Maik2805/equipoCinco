package com.univalle.grupocinco.dogapp.repository

import com.univalle.grupocinco.dogapp.network.DogApiService
import jakarta.inject.Inject

class DogRepository @Inject constructor(private val apiService: DogApiService) {

    suspend fun getAllBreeds(): List<String> {
        val response = apiService.getBreeds()
        if (response.isSuccessful) {
            val breedsMap = response.body()?.message ?: emptyMap()
            val breedList = mutableListOf<String>()

            for ((breed, subBreeds) in breedsMap) {
                if (subBreeds.isEmpty()) {
                    breedList.add(breed.capitalize())
                } else {
                    subBreeds.forEach { subBreed ->
                        breedList.add("${breed.capitalize()} ${subBreed.capitalize()}")
                    }
                }
            }

            return breedList.sorted()
        } else {
            throw Exception("Error en la respuesta del servidor")
        }
    }
}
