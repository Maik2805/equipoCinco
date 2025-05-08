package com.univalle.grupocinco.dogapp.network

import com.univalle.grupocinco.dogapp.data.model.DogBreedsResponse
import retrofit2.Response
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<DogBreedsResponse>

}
