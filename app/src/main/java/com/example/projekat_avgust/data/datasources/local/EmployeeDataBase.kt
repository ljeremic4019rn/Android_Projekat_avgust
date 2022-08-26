package com.example.projekat_avgust.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projekat_avgust.data.models.EmployeeEntity

@Database(
    entities = [EmployeeEntity::class],
    version = 1,
    exportSchema = false)

@TypeConverters()
abstract class EmployeeDataBase : RoomDatabase(){
    abstract fun getEmployeeDao(): EmployeeDao
}