package com.example.projekat_avgust.data.repositories

import android.annotation.SuppressLint
import com.example.projekat_avgust.data.datasources.local.EmployeeDao
import com.example.projekat_avgust.data.datasources.remote.EmployeeDataSource
import com.example.projekat_avgust.data.models.*
import com.example.projekat_avgust.data.models.responseRequest.EmployeeRequestUpdate
import io.reactivex.Completable
import io.reactivex.Observable

class EmployeeRepositoryImpl(private val localDataSource: EmployeeDao, private val remoteDataSource: EmployeeDataSource) : EmployeeRepository {

    override fun fetchAllFromServer(): Observable<Resource<Unit>> {
        return remoteDataSource
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
        return remoteDataSource.delete("https://dummy.restapiexample.com/api/v1/delete/${employeeId}")
            .map {
                Resource.Success(Unit)
            }
    }

    override fun update(employeeId: Long, name: String, salary: Int, age: Int): Observable<EmployeeResponse> {
        return remoteDataSource
            .update(
                "https://dummy.restapiexample.com/api/v1/update/${employeeId}",
                EmployeeRequestUpdate(employeeId, name, salary, age, "notNull")
            )
            .map {
                EmployeeResponse(employeeId ,it.data.employee_name, it.data.employee_salary ,it.data.employee_age, it.data.profile_image)
            }
    }

    override fun details(employeeId: Long): Observable<EmployeeResponse> {
        return remoteDataSource
            .details(
                "https://dummy.restapiexample.com/api/v1/employee/${employeeId}"
            )
            .map {
                it.data
            }
    }

    override fun add(employee: Employee): Observable<EmployeeResponse> {
        return remoteDataSource
            .addEmployee(
                "https://dummy.restapiexample.com/api/v1/create",
                EmployeeRequestUpdate(employee.id, employee.name, employee.salary.toInt(), employee.age.toInt(), "notNull")
            )
            .map {
                it.data
            }
    }

    override fun deleteById(id: Long): Completable {
        return localDataSource.deleteById(id)
    }

    override fun updateById(id: Long, name: String, salary: Int, age: Int): Completable {
        return localDataSource.update(id, name, salary, age)
    }

    override fun addToDb(id: Long, name: String, salary: Int, age: Int): Completable {
        return localDataSource.insert(EmployeeEntity(id, name, salary.toString(), age.toString(), ""))
    }

}