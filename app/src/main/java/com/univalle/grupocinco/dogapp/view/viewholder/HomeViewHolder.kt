package com.univalle.grupocinco.dogapp.view.viewholder

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univalle.grupocinco.dogapp.R
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment
import com.univalle.grupocinco.dogapp.databinding.FragmentCardBinding
import com.univalle.grupocinco.dogapp.network.DogRandomApiService
import com.univalle.grupocinco.dogapp.network.DogResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewHolder(binding: FragmentCardBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
        val bindingAppointment = binding
        val navController = navController

        private val apiImages: DogRandomApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(DogRandomApiService::class.java)
        }

        fun setAppointment(appointment: DogAppointment) {

            println("raza: " + appointment.breed)
            bindingAppointment.petNameTextView.text = appointment.dogName
            bindingAppointment.appointmentTextView.text = "# ${appointment.id}"
            bindingAppointment.symptomDescriptionTextView?.text = appointment.symptom
            println(appointment.breed::class.qualifiedName)
            getRandomDogImage(appointment.breed)
            bindingAppointment.itemCardView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("appointment", appointment)
                navController.navigate(
                    R.id.action_appointmentManagerFragment_to_detailAppointmentFragment, bundle
                )
            }

        }

        private fun getRandomDogImage(breed: String) {

            apiImages.getRandomDogImage(breed).enqueue(object : Callback<DogResponse> {
                override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {
                    println(call)
                    if (response.isSuccessful) {
                        response.body()?.message?.let { imageUrl ->
                            Glide.with(itemView.context)
                                .load(imageUrl)
                                .into(bindingAppointment.petImageView)
                                .clearOnDetach()
                        }
                    } else {
                        println("No entro")
                    }
                }

                override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                    Log.e("API Failure", "Error loading image", t)
                }
            })
        }
    }
=======
import com.univalle.grupocinco.dogapp.repository.DogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewHolder(
    private val binding: FragmentCardBinding,
    private val navController: NavController,
    private val dogRepository: DogRepository
) : RecyclerView.ViewHolder(binding.root) {

    fun setAppointment(appointment: DogAppointment) {
        binding.petNameTextView.text = appointment.dogName
        binding.appointmentTextView.text = "# ${appointment.id}"
        binding.symptomDescriptionTextView?.text = appointment.symptom
        getRandomDogImage(appointment.breed)
        binding.itemCardView.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("appointment", appointment)
            }
            navController.navigate(
                R.id.action_appointmentManagerFragment_to_detailAppointmentFragment,
                bundle
            )
        }
    }

    private fun getRandomDogImage(breed: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val imageUrl: String? = dogRepository.getBreedImage(breed.lowercase())
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(binding.petImageView)
                    .clearOnDetach()
            } catch (e: Exception) {
                println("No se pudo cargar la imagen")
            }
        }
    }

}

