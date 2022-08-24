package com.example.projekat_avgust.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.EmployeeItemBinding
import com.example.projekat_avgust.presentation.view.recycler.diff.EmployeeDiffCallback
import com.example.projekat_avgust.presentation.view.recycler.viewholder.EmployeeViewHolder

class EmployeeAdapter (val openDetailed: (stock: Employee) -> Unit) : ListAdapter<Employee, EmployeeViewHolder>(EmployeeDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemBinding = EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(itemBinding) { openDetailed(getItem(it)) }
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}