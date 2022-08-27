package com.example.projekat_avgust.data.repositories

import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.EmployeeResponse
import com.example.projekat_avgust.data.models.Resource
import io.reactivex.Completable
import io.reactivex.Observable

interface EmployeeRepository {
    fun fetchAllFromServer(): Observable<Resource<Unit>>
    fun delete(employeeId: Long): Observable<Resource<Unit>>
    fun update(employeeId: Long, name: String, salary: Int, age: Int): Observable<EmployeeResponse>
    fun details(employeeId: Long): Observable<EmployeeResponse>
    fun add(employee: Employee): Observable<EmployeeResponse>

    fun getAll(): Observable<List<Employee>>
    fun deleteById(id: Long): Completable
    fun getFromId (id: Long): Observable<Employee>
    fun updateById(id: Long, name: String, salary: Int, age: Int): Completable
    fun addToDb(id: Long, name: String, salary: Int, age: Int): Completable
}