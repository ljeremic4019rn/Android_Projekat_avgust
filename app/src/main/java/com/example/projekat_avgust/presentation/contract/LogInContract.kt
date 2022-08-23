package com.example.projekat_avgust.presentation.contract

import androidx.lifecycle.LiveData
import com.example.projekat_avgust.presentation.view.states.LogInState

interface LogInContract {

    interface LogInViewModel{
        val logInState: LiveData<LogInState>
        fun userAuth(username: String, password: String): Boolean
    }
}