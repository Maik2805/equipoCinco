package com.univalle.grupocinco.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.univalle.grupocinco.dogapp.viewmodel.DogBreedsViewModel
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.univalle.grupocinco.dogapp.R
import com.univalle.grupocinco.dogapp.databinding.FragmentAppointmentDetailBinding
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentDetailBinding
    private val appointmentViewModel: DogBreedsViewModel by viewModels()
    private lateinit var receivedAppointment: DogAppointment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            getAppointmentData()

            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            binding.deleteButton.setOnClickListener {
                deleteAppointment()
            }

            binding.editButton.setOnClickListener {
                if (::receivedAppointment.isInitialized) {
                    val bundle = Bundle()
                    bundle.putSerializable("appointmentData", receivedAppointment)
                    Log.d("EditButton", "Navigating to edit with data: $receivedAppointment")
                    findNavController().navigate(
                        R.id.action_detailAppointmentFragment_to_editAppointmentFragment,
                        bundle
                    )
                } else {
                    Log.e("EditButton", "Received appointment is not initialized!")
                    Snackbar.make(binding.root, "Error: No se puede editar la cita", Snackbar.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("DetailFragment", "Error in onViewCreated", e)
            Snackbar.make(view, "Error al cargar los datos", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getAppointmentData() {
        try {
            val receivedBundle = arguments
            if (receivedBundle != null && receivedBundle.containsKey("appointment")) {
                @Suppress("DEPRECATION")
                receivedAppointment = receivedBundle.getSerializable("appointment") as DogAppointment

                binding.numberAppointment.text = "#${receivedAppointment.id}"
                binding.titleTextDetailsName.text = receivedAppointment.dogName
                binding.petBreedName.text = receivedAppointment.breed
                binding.petSymptoms.text = receivedAppointment.symptom
                binding.ownerName.text = "Propietario: ${receivedAppointment.ownerName}"
                binding.ownerPhone.text = "Teléfono: ${receivedAppointment.phone}"
                appointmentViewModel.getBreedImage(receivedAppointment.breed,onSuccess = { imageUrl ->
                    Glide.with(this@DetailFragment)
                        .load(imageUrl)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(binding.petBreedImage)
                },
                    onError = { error ->
                        binding.petBreedImage.setImageResource(R.drawable.logo)

                    } )

            } else {
                Log.e("DetailFragment", "No appointment data found in arguments")
                Snackbar.make(binding.root, "Error: No se encontraron datos de la cita", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        } catch (e: Exception) {
            Log.e("DetailFragment", "Error getting appointment data", e)
            Snackbar.make(binding.root, "Error al obtener datos de la cita", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun deleteAppointment() {
        try {
            val rootView = binding.root

            appointmentViewModel.deleteAppointment(receivedAppointment,
                onSuccess = {
                    Snackbar.make(rootView, "Cita eliminada exitosamente", Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                },
                onError = { error ->
                    Snackbar.make(rootView, "Error al eliminar: ${error.message}", Snackbar.LENGTH_LONG).show()
                })
        } catch (e: Exception) {
            Log.e("DetailFragment", "Error deleting appointment", e)
            Snackbar.make(binding.root, "Error al eliminar la cita", Snackbar.LENGTH_SHORT).show()
        }
    }

}