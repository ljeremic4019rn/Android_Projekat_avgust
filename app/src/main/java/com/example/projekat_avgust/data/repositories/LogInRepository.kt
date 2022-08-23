package com.example.projekat_avgust.data.repositories

import com.example.projekat_avgust.data.models.User
import io.reactivex.Observable

interface LogInRepository {

    fun userAuth(username:String, password:String): Observable<User>
}