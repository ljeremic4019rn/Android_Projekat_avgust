package com.example.projekat_avgust.data.models.responseRequest

import com.example.projekat_avgust.data.models.EmployeeResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeUpdateRequest(
    val employee_name: String,
    val employee_salary: Int,
    val employee_age: Int,
)
