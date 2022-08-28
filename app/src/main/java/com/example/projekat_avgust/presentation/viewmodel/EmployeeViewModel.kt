package com.example.projekat_avgust.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.data.models.Resource
import com.example.projekat_avgust.data.repositories.EmployeeRepository
import com.example.projekat_avgust.presentation.contract.EmployeeContract
import com.example.projekat_avgust.presentation.view.states.EmployeeState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class EmployeeViewModel  (private val employeeRepository: EmployeeRepository ) : ViewModel(), EmployeeContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val employeeState: MutableLiveData<EmployeeState> = MutableLiveData()
    override val newEmployees: MutableLiveData<List<Employee>> = MutableLiveData()
    override val gradualRvList: MutableLiveData<List<Employee>> = MutableLiveData()
    override var allEmployeesLocal: List<Employee> = arrayListOf()

    private var tempList: ArrayList<Employee> = arrayListOf()
    private var sizeCounter = 0
    private var displayOnlyOnce: Boolean = false


    /*
    svaka akcija ide na server prvo a zatim kada se vrati success
    poziva se akcija koja vraca podatke iz lokalne baze kako bi se imali prikazani izmenjeni podaci
     */


    override fun fetchAllEmployeesFromServer() {//sa servera
        val subscription = employeeRepository
            .fetchAllFromServer()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> employeeState.value = EmployeeState.Loading
                        is Resource.Success -> employeeState.value = EmployeeState.DataFetched
                        is Resource.Error -> employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllEmployees() {//iz baze
        val subscription = employeeRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    employeeState.value = EmployeeState.Success(it)
                },
                {
                    employeeState.value = EmployeeState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun load10Employees(initial: Boolean){//ucitava 10 po 10 employeea u main recycler, paginacija
        val tmpArrayList: ArrayList<Employee> = arrayListOf()
        when {
            initial -> sizeCounter = 9
            sizeCounter + 10 <= allEmployeesLocal.size -> sizeCounter += 10
            else -> sizeCounter += (allEmployeesLocal.size - sizeCounter)
        }

        println("counter $sizeCounter list ${allEmployeesLocal.size}")


        if (sizeCounter <= allEmployeesLocal.size) {
            for (i in 0 until sizeCounter)
                tmpArrayList.add(allEmployeesLocal[i])

            gradualRvList.value = tmpArrayList
        }

    }



    override fun deleteEmployee(employeeId: Long) {
        val subscription = employeeRepository
            .delete(employeeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> {
                            deleteFromDb(employeeId)
                            employeeState.value = EmployeeState.Deleted(employeeId)
                        }
                        is Resource.Error -> employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    private fun deleteFromDb(employeeId: Long){
        val subscription = employeeRepository
            .deleteById(employeeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("DELETED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    private fun updateFromDb(employeeId: Long, name: String, salary: Int, age: Int){
        val subscription = employeeRepository
            .updateById(employeeId, name, salary, age)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("UPDATED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateEmployee(employeeId: Long, name: String, salary: Int, age: Int) {
        val subscription = employeeRepository
            .update(employeeId, name, salary, age)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    employeeState.value = EmployeeState.Updated(it)
                    updateFromDb(employeeId, name, salary, age)
                },
                {
                    employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun detailedEmployee(employeeId: Long) {
        val subscription = employeeRepository
            .details(employeeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//                    displayOnlyOnce = true
//                    detailedFromDbb(employeeId)
                    employeeState.value = EmployeeState.Detailed(it)
                },
                {
                    employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    //po zahtevu profesora ne vuku se podaci iz lokalne baze engo samo sa api-a !!!
    private fun detailedFromDbb (id: Long){
            val subscription = employeeRepository
                .getFromId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                        //ovaj check je zato sto ima bug da se ovaj blok aktivira randomly kada se pokrenu druge akcije iz baze
                        if (displayOnlyOnce) {
                            Timber.e("DETAILED")
//                            employeeState.value = EmployeeState.Detailed(it)
                        }
                        displayOnlyOnce = false
                    },
                    {
                        Timber.e(it)
                    }
                )
            subscriptions.add(subscription)
    }

    override fun addNewEmployee(employee: Employee) {
        val subscription = employeeRepository
            .add(employee)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    employeeState.value = EmployeeState.Created(it)
                    addToDb(employee.id, employee.name, employee.salary.toInt(), employee.age.toInt())
                },
                {
                    employeeState.value = EmployeeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    private fun addToDb(id: Long, name: String, salary: Int, age: Int){
        val subscription = employeeRepository
            .addToDb(id, name, salary, age)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("CREATED")
                    addEmployeeToTempList(Employee(id, name, salary.toString(), age.toString(), ""))
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    /*
    kada se doda novi employee stavljamo ga u live data listu koju prikazujemo u recycler
    i aktiviramo thread koji ga nakon 2 min skloni iz te liste
     */
    private fun addEmployeeToTempList(employee: Employee){
        tempList.add(0,employee)
        newEmployees.value = tempList//dodamo ga na listu

        Handler().postDelayed({//cekamo da se izbrise i stvimo novu listu
            if (tempList.contains(employee)){
                tempList.remove(employee)

                newEmployees.value = tempList
            }
        },120000)
        //10000 - 10 sec
        //120000 - 2 min
    }
}