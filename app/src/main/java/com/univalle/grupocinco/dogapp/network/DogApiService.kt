package com.univalle.grupocinco.dogapp.network

import com.univalle.grupocinco.dogapp.data.model.DogBreedsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<DogBreedsResponse>

}

interface DogRandomApiService {
    @GET("breed/{breed}/images/random")
    fun getRandomDogImage(@Path("breed") breed: String): Call<DogResponse>
}

data class DogResponse(
    val status: String,
    val message: String
)