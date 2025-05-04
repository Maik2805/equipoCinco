package com.univalle.grupocinco.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.grupocinco.dogapp.network.RetrofitClient
import com.univalle.grupocinco.dogapp.repository.DogRepository
import kotlinx.coroutines.launch

class DogBreedsViewModel : ViewModel() {

    private val repository = DogRepository(RetrofitClient.apiService)

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> get() = _breeds

    fun loadBreeds() {
        viewModelScope.launch {
            try {
                _breeds.value = repository.getAllBreeds()
            } catch (e: Exception) {
                println("Error al cargar las razas de perro: ${e.message}")
                _breeds.value = emptyList()
            }
        }
    }
}
