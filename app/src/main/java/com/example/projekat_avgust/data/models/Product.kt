package com.example.projekat_avgust.data.models

import android.provider.MediaStore
import com.squareup.moshi.JsonClass
import java.util.concurrent.CountDownLatch

@JsonClass(generateAdapter = true)
 data class Product(
  val id: Long,
  val title: String,
  val description: String,
  val price: String,
  val discountPercentage: String,
  val rating: String,
  val stock: String,
  val brand: String,
  val category: String,
  val thumbnail: String,
  val images: List<String>,
  val count: Long = 0
 )
