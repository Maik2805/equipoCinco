package com.univalle.grupocinco.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.univalle.grupocinco.dogapp.R
import com.univalle.grupocinco.dogapp.databinding.FragmentHomeBinding
import com.univalle.grupocinco.dogapp.repository.DogRepository
import com.univalle.grupocinco.dogapp.view.adapter.AppointmentAdapter
import com.univalle.grupocinco.dogapp.viewmodel.DogBreedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val appointmentViewModel: DogBreedsViewModel by viewModels()

    @Inject lateinit var dogRepository: DogRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllers()
        observerViewModel()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun controllers() {
        binding.btnAddAppointment.setOnClickListener {
            try {
                Log.d("NAVIGATION", "Trying to navigate now")
                findNavController().navigate(R.id.action_appointmentManagerFragment_to_addAppointmentFragment)
            } catch (e: Exception) {
                Log.e("NAVIGATION", "Navigation failed", e)
            }
        }
    }

    private fun observerViewModel(){
        observerListAppointment()
    }

    private fun observerListAppointment(){
        appointmentViewModel.listAppointment(
            onSuccess = { listAppointment ->
                val recycler = binding.recyclerview
                val layoutManager = LinearLayoutManager(context)
                recycler.layoutManager = layoutManager
                val adapter = AppointmentAdapter(listAppointment.toMutableList(), findNavController(), dogRepository)
                recycler.adapter = adapter
                adapter.notifyDataSetChanged()
            },
            onError = { error ->
                Log.e("AppointmentError", "Error al cargar citas: ${error.message}")
            }
        )
    }

}