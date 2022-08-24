package com.example.projekat_avgust.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.projekat_avgust.data.models.Employee

class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>(){


    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return  oldItem.name == newItem.name &&
                oldItem.age == newItem.age &&
                oldItem.salary == newItem.salary &&
                oldItem.image == newItem.image
    }


}