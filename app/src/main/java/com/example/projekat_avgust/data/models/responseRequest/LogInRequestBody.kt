package com.example.projekat_avgust.data.models.responseRequest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInRequestBody(
    val username: String,
    val password: String
    )
