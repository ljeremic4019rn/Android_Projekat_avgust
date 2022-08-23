package com.example.projekat_avgust.presentation.view.states

import com.example.projekat_avgust.data.models.User

sealed class LogInState {
    object Loading: LogInState()
    object DataFetched: LogInState()
    data class Success(val user: User): LogInState()
    data class Error(val message: String): LogInState()
}