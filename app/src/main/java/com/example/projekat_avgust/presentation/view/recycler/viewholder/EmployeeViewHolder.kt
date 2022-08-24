package com.example.projekat_avgust.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.EmployeeItemBinding

class EmployeeViewHolder (private val itemBinding: EmployeeItemBinding, val openDetailed: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root){

    init {
        itemBinding.root.setOnClickListener {
            openDetailed(layoutPosition)
        }
    }


    @SuppressLint("SetTextI18n")
    fun bind(employee: Employee){
        itemBinding.nameTv.text = "Name: " + employee.name
        itemBinding.salaryTv.text = "Salary: " + employee.salary
        itemBinding.ageTv.text = "Age: " + employee.age

    }
}