package com.example.projekat_avgust.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse (
    @field:Json(name = "products") val item: List<Product>
    )
