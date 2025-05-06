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
import com.univalle.grupocinco.dogapp.R
import com.univalle.grupocinco.dogapp.databinding.FragmentAddAppointmentBinding
import com.univalle.grupocinco.dogapp.viewmodel.DogBreedsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAppointmentFragment : Fragment() {
    private lateinit var dogViewModel: DogBreedsViewModel

    private lateinit var binding: FragmentAddAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedinstancestate: Bundle?
    ): View? {
        binding = FragmentAddAppointmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dogViewModel = ViewModelProvider(this)[DogBreedsViewModel::class.java]
        dogViewModel.loadBreeds()

        dogViewModel.breeds.observe(viewLifecycleOwner) { breedlist ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedlist)
            binding.razaAutoCompleteTextView.setAdapter(adapter)
        }

        binding.btnBack.setOnClickListener() {
            findNavController().popBackStack()
        }

        controllers()
    }

    private fun controllers() {
        validatedata()
    }

    private fun validatedata() {
        val listedittext = listOf(
            binding.nameEditText,
            binding.nameOwnerEditText,
            binding.razaAutoCompleteTextView,
            binding.telephoneEditText
        )

        fun isformfilled(): Boolean {
            return listedittext.all { it.text.isNotEmpty() }
        }

        listedittext.forEach() { editText ->
            editText.addTextChangedListener() {
                binding.btnSaveAppointment.isEnabled = isformfilled()
            }
        }

        binding.btnSaveAppointment.isEnabled = isformfilled()

        binding.btnSaveAppointment.setOnClickListener() {
            val selectedsymptom = binding.sintomas.selectedItem.toString()

            if (selectedsymptom == getString(R.string.placeholder_symptom)) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.alert_symptom_required),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                //todo guardar la cita en la base de datos
            }
        }
    }

}