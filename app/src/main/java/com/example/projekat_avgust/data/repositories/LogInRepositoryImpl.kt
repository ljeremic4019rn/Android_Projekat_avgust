package com.example.projekat_avgust.data.repositories

import com.example.projekat_avgust.data.datasources.remote.LogInDataSource
import com.example.projekat_avgust.data.models.responseRequest.LogInRequestBody
import com.example.projekat_avgust.data.models.User
import io.reactivex.Observable

class LogInRepositoryImpl (
    private val logInDataSource: LogInDataSource
        ): LogInRepository{
    override fun userAuth(username: String, password: String): Observable<User> {
        return logInDataSource
            .userAuth(
                "https://dummyjson.com/auth/login",
                LogInRequestBody(username, password)
            )
            .map { postResponse ->
                User(
                    userId = postResponse.id,
                    username = postResponse.username,
                    email = postResponse.email,
                    firstName = postResponse.firstName,
                    lastName =  postResponse.lastName,
                    gender = postResponse.gender,
                    image = postResponse.image,
                    token = postResponse.token

                )
            }

    }


}