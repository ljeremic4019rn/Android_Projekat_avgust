package com.example.projekat_avgust.presentation.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.presentation.view.states.EmployeeState

interface EmployeeContract {
    interface ViewModel {
        val employeeState: LiveData<EmployeeState>

        var testVar: MutableLiveData<Int>

        fun fetchAllEmployeesFromServer()
        fun getAllEmployees()
        fun deleteEmployee(employeeId: Long)
        fun updateEmployee(employeeId: Long, employeeDetails: Employee)
        fun detailedEmployee(employeeId: Long)
    }
}