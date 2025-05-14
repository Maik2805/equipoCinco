package com.univalle.grupocinco.dogapp.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univalle.grupocinco.dogapp.R
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment
import com.univalle.grupocinco.dogapp.databinding.FragmentCardBinding
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