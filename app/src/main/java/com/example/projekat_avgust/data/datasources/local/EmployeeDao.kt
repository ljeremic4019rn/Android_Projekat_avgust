package com.example.projekat_avgust.data.datasources.local

import androidx.room.*
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.EmployeeEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class EmployeeDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<EmployeeEntity>): Completable

    @Query("SELECT * FROM employees")
    abstract fun getAll(): Observable<List<EmployeeEntity>>

    @Query("SELECT * FROM employees WHERE id == :id")
    abstract fun getById(id: Long): Observable<EmployeeEntity>

    @Query("DELETE FROM employees")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(employeeEntity: EmployeeEntity): Completable

    @Query("DELETE FROM employees WHERE id == :id")
    abstract fun deleteById(id: Long): Completable

    @Query("UPDATE employees SET name = :name, salary = :salary, age = :age  WHERE id == :id ")
    abstract fun update(id: Long, name: String, salary: Int, age: Int): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<EmployeeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}