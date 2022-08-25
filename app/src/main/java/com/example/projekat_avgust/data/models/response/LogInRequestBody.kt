package com.example.projekat_avgust.data.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInRequestBody(
    val username: String,
    val password: String
    )
