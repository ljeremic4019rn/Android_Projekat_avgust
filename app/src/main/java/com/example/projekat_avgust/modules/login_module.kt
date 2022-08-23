package com.example.projekat_avgust.modules

import com.example.projekat_avgust.data.datasources.remote.LogInDataSource
import com.example.projekat_avgust.data.repositories.LogInRepository
import com.example.projekat_avgust.data.repositories.LogInRepositoryImpl
import com.example.projekat_avgust.presentation.viewmodel.LogInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val logInModule = module {


    viewModel { LogInViewModel(logInRepository = get()) }

    single<LogInRepository> { LogInRepositoryImpl(logInDataSource = get()) }

    single<LogInDataSource> { create(retrofit = get()) }


}