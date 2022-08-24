package com.example.projekat_avgust.data.repositories

import com.example.projekat_avgust.data.datasources.remote.EmployeeDataSource
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.EmployeeEntity
import com.example.projekat_avgust.data.models.EmployeeResponse
import com.example.projekat_avgust.data.models.Resource
import com.example.rmaproject2.data.datasource.local.EmployeeDao
import io.reactivex.Observable
import timber.log.Timber

class EmployeeRepositoryImpl(private val localDataSource: EmployeeDao, private val remoteDataSource: EmployeeDataSource) : EmployeeRepository {

    override fun fetchAllFromServer(): Observable<Resource<Unit>> {
//        val entities : MutableList<EmployeeEntity> = mutableListOf()


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
}