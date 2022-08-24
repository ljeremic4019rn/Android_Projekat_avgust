package com.example.projekat_avgust.modules

import com.example.projekat_avgust.data.datasources.remote.EmployeeDataSource
import com.example.projekat_avgust.data.datasources.remote.LogInDataSource
import com.example.projekat_avgust.data.repositories.EmployeeRepository
import com.example.projekat_avgust.data.repositories.EmployeeRepositoryImpl
import com.example.projekat_avgust.data.repositories.LogInRepository
import com.example.projekat_avgust.data.repositories.LogInRepositoryImpl
import com.example.projekat_avgust.presentation.viewmodel.EmployeeViewModel
import com.example.projekat_avgust.presentation.viewmodel.LogInViewModel
import com.example.rmaproject2.data.datasource.local.EmployeeDataBase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val employee_module = module {

    viewModel { EmployeeViewModel(employeeRepository = get()) }

    single<EmployeeRepository> { EmployeeRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<EmployeeDataBase>().getEmployeeDao() }

    single<EmployeeDataSource> { create(retrofit = get()) }

}