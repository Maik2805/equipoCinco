package com.univalle.grupocinco.dogapp.network

import com.univalle.grupocinco.dogapp.data.model.DogBreedsResponse
import com.univalle.grupocinco.dogapp.data.model.DogGeneralResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<DogBreedsResponse>

    @GET("breed/{breed}/images/random")
    suspend fun getRandomDogImageByBreed(@Path("breed") breed: String): Response<DogGeneralResponse>

    @GET("breed/{breed}/{subBreed}/images/random")
    suspend fun getRandomDogImageByBreedAndSubBreed(@Path("breed") breed: String, @Path("subBreed") subBreed: String): Response<DogGeneralResponse>
}