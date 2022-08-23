package com.example.projekat_avgust.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sacuvani")
data class ProbniEnty(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
//    val description: String,
//    val price: String,
//
//
//    val discountPercentage: String,
//    val rating: String,
//    val stock: String,
//    val brand: String,
//    val category: String,
//    val thumbnail: String,
//    val count: Long

)