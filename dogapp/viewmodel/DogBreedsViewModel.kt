package com.univalle.grupocinco.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment
import com.univalle.grupocinco.dogapp.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DogBreedsViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> get() = _breeds

    private val _listAppointment = MutableLiveData<List<DogAppointment>>()
    val listAppointment: LiveData<List<DogAppointment>> get() = _listAppointment

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

    fun createAppointment(appointment: DogAppointment, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insertAppointment(appointment)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun listAppointment(onSuccess: (List<DogAppointment>) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val appointments = repository.getAllAppointments()
                onSuccess(appointments)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

}
