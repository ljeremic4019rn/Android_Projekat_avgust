package com.example.projekat_avgust.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image (
    val images: List<String>
        )