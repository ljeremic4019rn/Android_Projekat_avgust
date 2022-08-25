package com.example.projekat_avgust.data.datasources.remote

import com.example.projekat_avgust.data.models.responseRequest.LogInRequestBody
import com.example.projekat_avgust.data.models.responseRequest.LogInResponseBody
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url


interface LogInDataSource {
    @POST
    fun userAuth(@Url url: String?, @Body body: LogInRequestBody): Observable<LogInResponseBody>
}