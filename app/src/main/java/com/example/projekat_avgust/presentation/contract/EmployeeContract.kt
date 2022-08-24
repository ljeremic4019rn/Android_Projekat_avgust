package com.example.projekat_avgust.presentation.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projekat_avgust.presentation.view.states.EmployeeState

interface EmployeeContract {
    interface ViewModel {
        val employeeState: LiveData<EmployeeState>

        var testVar: MutableLiveData<Int>

        fun fetchAllEmployeesFromServer()
        fun getAllEmployees()
    }
}