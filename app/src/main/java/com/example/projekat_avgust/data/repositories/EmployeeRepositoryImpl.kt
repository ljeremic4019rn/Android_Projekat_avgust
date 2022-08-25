package com.example.projekat_avgust.data.repositories

import android.annotation.SuppressLint
import com.example.projekat_avgust.data.datasources.remote.EmployeeDataSource
import com.example.projekat_avgust.data.models.*
import com.example.rmaproject2.data.datasource.local.EmployeeDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.internal.notifyAll

class EmployeeRepositoryImpl(private val localDataSource: EmployeeDao, private val remoteDataSource: EmployeeDataSource) : EmployeeRepository {

    override fun fetchAllFromServer(): Observable<Resource<Unit>> {
        return remoteDataSource//todo dodaj check za success
            .getAll(
                "https://dummy.restapiexample.com/api/v1/employees"
            )
            .map {
                val entities = it.data.map { employeeResponse ->
                    EmployeeEntity(
                        id = employeeResponse.id,
                        name = employeeResponse.employee_name,
                        salary = employeeResponse.employee_salary,
                        age = employeeResponse.employee_age,
                        image = employeeResponse.profile_image
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Employee>> {
        return localDataSource
            .getAll()
            .map { it ->
                it.map {
                    Employee(
                        it.id,
                        it.name,
                        it.salary,
                        it.age,
                        it.image
                    )
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun delete(employeeId: Long): Observable<Resource<Unit>> {
//todo
        return remoteDataSource.delete("https://dummy.restapiexample.com/api/v1/delete/${employeeId}")
            .map {
                if (it.status == "success"){
                    localDataSource.deleteById(employeeId)
                }
            }
            .map {
                Resource.Success(Unit)
            }

    }

    override fun update(employeeId: Long, employeeDetails: Employee): Completable {
        TODO("Not yet implemented")
    }

    override fun details(employeeId: Long): Observable<EmployeeResponse> {
        return remoteDataSource//todo dodaj check za success
            .details(
                "https://dummy.restapiexample.com/api/v1/employee/${employeeId}"
            )
            .map {
                it.data
            }
    }

}