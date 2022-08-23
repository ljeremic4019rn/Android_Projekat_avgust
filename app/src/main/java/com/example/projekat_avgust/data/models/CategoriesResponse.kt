package com.example.projekat_avgust.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class CategoriesResponse (
    val list: List<String>
)