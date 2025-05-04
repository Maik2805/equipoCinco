package com.univalle.grupocinco.dogapp.model

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
