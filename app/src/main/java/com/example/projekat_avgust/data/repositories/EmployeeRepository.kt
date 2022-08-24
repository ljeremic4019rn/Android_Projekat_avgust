package com.example.projekat_avgust.data.repositories

import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.Resource
import io.reactivex.Observable

interface EmployeeRepository {
    fun fetchAllFromServer(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Employee>>
}