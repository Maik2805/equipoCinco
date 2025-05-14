package com.univalle.grupocinco.dogapp.data.model

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
