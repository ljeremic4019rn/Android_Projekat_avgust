package com.example.projekat_avgust.data.models.responseRequest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInResponseBody(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token:String
)
