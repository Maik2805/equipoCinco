package com.univalle.grupocinco.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment
import com.univalle.grupocinco.dogapp.databinding.FragmentEditAppointmentBinding
import com.univalle.grupocinco.dogapp.viewmodel.DogBreedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.univalle.grupocinco.dogapp.R

@AndroidEntryPoint
class EditAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentEditAppointmentBinding
    private lateinit var dogViewModel: DogBreedsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dogViewModel = ViewModelProvider(this)[DogBreedsViewModel::class.java]

        val appointment = arguments?.getSerializable("appointmentData") as? DogAppointment
        if (appointment == null) {
            Snackbar.make(binding.root, "Error: cita no encontrada", Snackbar.LENGTH_LONG).show()
            findNavController().popBackStack()
            return
        }

        // Cargar razas desde el ViewModel
        dogViewModel.loadBreeds()
        dogViewModel.breeds.observe(viewLifecycleOwner) { breeds ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breeds)
            binding.razaAutoCompleteTextView.setAdapter(adapter)
        }

        // Mostrar los datos de la cita
        binding.nameEditText.setText(appointment.dogName)
        binding.razaAutoCompleteTextView.setText(appointment.breed, false)
        binding.nameOwnerEditText.setText(appointment.ownerName)
        binding.telephoneEditText.setText(appointment.phone.toString())

        // Botón de volver
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        setupValidation(appointment)
    }

    private fun setupValidation(existingAppointment: DogAppointment) {
        val campos = listOf(
            binding.nameEditText,
            binding.nameOwnerEditText,
            binding.razaAutoCompleteTextView,
            binding.telephoneEditText
        )

        fun isFormularioValido(): Boolean {
            return campos.all { it.text?.isNotEmpty() == true }
        }

        campos.forEach { campo ->
            campo.addTextChangedListener {
                binding.btnEditAppointment.isEnabled = isFormularioValido()
            }
        }

        binding.btnEditAppointment.isEnabled = isFormularioValido()

        binding.btnEditAppointment.setOnClickListener {
            val updatedAppointment = existingAppointment.copy(
                dogName = binding.nameEditText.text.toString(),
                breed = binding.razaAutoCompleteTextView.text.toString(),
                ownerName = binding.nameOwnerEditText.text.toString(),
                phone = binding.telephoneEditText.text.toString().toLong()
            )

            dogViewModel.updateAppointment(
                updatedAppointment,
                onSuccess = {
                    Snackbar.make(binding.root, "Cita actualizada correctamente", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editAppointmentFragment_to_appointmentManagerFragment)
                },
                onError = { error ->
                    Snackbar.make(binding.root, "Error al actualizar: ${error.message}", Snackbar.LENGTH_LONG).show()
                }
            )
        }
    }
}
