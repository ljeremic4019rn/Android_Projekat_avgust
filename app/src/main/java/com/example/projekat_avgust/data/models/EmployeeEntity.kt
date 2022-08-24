package com.example.projekat_avgust.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val salary: String,
    val age: String,
    val image: String
)
