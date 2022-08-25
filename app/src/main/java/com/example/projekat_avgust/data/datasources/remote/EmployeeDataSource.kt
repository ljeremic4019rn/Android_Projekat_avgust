package com.example.projekat_avgust.data.datasources.remote

import com.example.projekat_avgust.data.models.response.EmployeeResponseObject
import com.example.projekat_avgust.data.models.response.EmployeeResponseSingle
import com.example.projekat_avgust.data.models.response.LogInRequestBody
import io.reactivex.Observable
import retrofit2.http.*


interface EmployeeDataSource {
    @GET
    fun getAll(@Url url: String?): Observable<EmployeeResponseObject>

    @DELETE
    fun delete(@Url url: String?): Observable<EmployeeResponseSingle>

    @PUT
    fun update(@Url url: String?, @Body body: LogInRequestBody): Observable<EmployeeResponseSingle>

    @GET
    fun details(@Url url: String?): Observable<EmployeeResponseSingle>
}