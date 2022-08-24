package com.example.rmaproject2.data.datasource.local

import androidx.room.*
import com.example.projekat_avgust.data.models.EmployeeEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class EmployeeDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<EmployeeEntity>): Completable

    @Query("SELECT * FROM employees")
    abstract fun getAll(): Observable<List<EmployeeEntity>>

    @Query("DELETE FROM employees")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<EmployeeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }


}