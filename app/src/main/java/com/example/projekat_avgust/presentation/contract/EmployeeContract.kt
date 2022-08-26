package com.example.projekat_avgust.presentation.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.presentation.view.states.EmployeeState

interface EmployeeContract {
    interface ViewModel {
        val employeeState: LiveData<EmployeeState>
        val newEmployees: LiveData<List<Employee>>
        val gradualRvList: MutableLiveData<List<Employee>>
        var allEmployeesLocal: List<Employee>

        fun fetchAllEmployeesFromServer()
        fun getAllEmployees()
        fun deleteEmployee(employeeId: Long)
        fun updateEmployee(employeeId: Long, name: String, salary: Int, age: Int)
        fun detailedEmployee(employeeId: Long)
        fun addNewEmployee(employee: Employee)
        fun load10Employees(initial: Boolean)
    }
}