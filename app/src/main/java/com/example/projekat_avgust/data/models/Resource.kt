package com.example.projekat_avgust.data.models

sealed class Resource<out T>{

    //Kotlinov nacin da se napravi neka struktura, da s epodaci vrepuju u neku strukture
    //da opisemoi neku vrstu hijerarhije
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val message: String = "") : Resource<T>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): Resource<T>()
}