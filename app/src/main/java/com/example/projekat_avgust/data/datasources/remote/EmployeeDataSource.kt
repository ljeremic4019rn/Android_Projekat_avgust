package com.example.projekat_avgust.data.datasources.remote

import com.example.projekat_avgust.data.models.responseRequest.EmployeeResponseDelete
import com.example.projekat_avgust.data.models.responseRequest.EmployeeResponseObject
import com.example.projekat_avgust.data.models.responseRequest.EmployeeResponseSingle
import com.example.projekat_avgust.data.models.responseRequest.LogInRequestBody
import io.reactivex.Observable
import retrofit2.http.*


interface EmployeeDataSource {
    @GET
    fun getAll(@Url url: String?): Observable<EmployeeResponseObject>

    @DELETE
    fun delete(@Url url: String?): Observable<EmployeeResponseDelete>

    @PUT
    fun update(@Url url: String?, @Body body: LogInRequestBody): Observable<EmployeeResponseSingle>

    @GET
    fun details(@Url url: String?): Observable<EmployeeResponseSingle>
}