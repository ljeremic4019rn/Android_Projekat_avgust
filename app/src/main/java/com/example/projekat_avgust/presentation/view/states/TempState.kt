package com.example.projekat_avgust.presentation.view.states

import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.EmployeeResponse

sealed class
TempState {
    object DataFetched: TempState()
    data class Success(val employees: List<Employee>): TempState()
    data class Deleted(val message: String): TempState()
}