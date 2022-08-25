package com.example.projekat_avgust.data.models.responseRequest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeResponseDelete(
    val status: String,
    val data: String,
    val message: String
)
