package com.example.projekat_avgust.presentation.view.states

import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.EmployeeResponse

sealed class
EmployeeState {
    object Loading: EmployeeState()
    object DataFetched: EmployeeState()
    data class Success(val employees: List<Employee>): EmployeeState()
    data class Detailed(val detailed: EmployeeResponse): EmployeeState()
    data class Deleted(val detailed: Long): EmployeeState()
    data class Updated(val detailed: EmployeeResponse): EmployeeState()
    data class Created(val detailed: EmployeeResponse): EmployeeState()
    data class Error(val message: String): EmployeeState()
}