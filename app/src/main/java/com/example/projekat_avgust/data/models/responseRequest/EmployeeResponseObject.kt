package com.example.projekat_avgust.data.models.responseRequest

import com.example.projekat_avgust.data.models.EmployeeResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeResponseObject(
    val status: String,
    val data: List<EmployeeResponse>,
    val message: String
)
