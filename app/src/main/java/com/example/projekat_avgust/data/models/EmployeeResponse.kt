package com.example.projekat_avgust.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeResponse(
    val id: Long,
    val employee_name: String,
    val employee_salary: String,
    val employee_age: String,
    val profile_image: String
)
