package com.example.projekat_avgust.data.datasources.remote

import com.example.projekat_avgust.data.models.EmployeeResponse
import com.example.projekat_avgust.data.models.EmployeeResponseObject
import com.example.projekat_avgust.data.models.LogInRequestBody
import com.example.projekat_avgust.data.models.LogInResponseBody
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


interface EmployeeDataSource {
    @GET
    fun getAll(@Url url: String?): Observable<EmployeeResponseObject>
}