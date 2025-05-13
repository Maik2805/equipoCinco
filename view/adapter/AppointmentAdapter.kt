package com.univalle.grupocinco.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.univalle.grupocinco.dogapp.data.entity.DogAppointment
import com.univalle.grupocinco.dogapp.databinding.FragmentCardBinding
import com.univalle.grupocinco.dogapp.repository.DogRepository
import com.univalle.grupocinco.dogapp.view.viewholder.HomeViewHolder

class AppointmentAdapter(
    private val listAppointment:MutableList<DogAppointment>,
    private val navController: NavController,
    private val repository: DogRepository
) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = FragmentCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding, navController, repository)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val appointment = listAppointment[position]
        holder.setAppointment(appointment)
    }

    override fun getItemCount(): Int {
        return listAppointment.size
    }
}
