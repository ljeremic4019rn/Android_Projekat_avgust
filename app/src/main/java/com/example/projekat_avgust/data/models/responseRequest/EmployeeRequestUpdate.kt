package com.example.projekat_avgust.data.models.responseRequest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeRequestUpdate(
    val id: Long,
    val employee_name: String,
    val employee_salary: Int,
    val employee_age: Int,
    val profile_image: String
)
