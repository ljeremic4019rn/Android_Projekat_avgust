package com.example.projekat_avgust.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Categories (
    val name: List<String>
        )