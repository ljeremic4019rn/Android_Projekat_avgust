package com.example.projekat_avgust.data.models

data class User (
    val userId: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token:String
)