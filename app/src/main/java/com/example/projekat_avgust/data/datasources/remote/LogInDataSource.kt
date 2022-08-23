package com.example.projekat_avgust.data.datasources.remote

import com.example.projekat_avgust.data.models.LogInRequestBody
import com.example.projekat_avgust.data.models.LogInResponseBody
import io.reactivex.Observable
import retrofit2.http.*

interface LogInDataSource {

    @POST("auth/login")
    fun userAuth(@Body body: LogInRequestBody): Observable<LogInResponseBody>


}