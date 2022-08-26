package com.example.projekat_avgust.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat_avgust.data.models.User
import com.example.projekat_avgust.data.repositories.LogInRepository
import com.example.projekat_avgust.presentation.contract.LogInContract
import com.example.projekat_avgust.presentation.view.states.LogInState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LogInViewModel(private val logInRepository: LogInRepository) : ViewModel(), LogInContract.LogInViewModel{

    private val subscriptions = CompositeDisposable()
    override val logInState: MutableLiveData<LogInState> = MutableLiveData()

    override fun userAuth(username: String, password: String): Boolean {

        val subscription = logInRepository
            .userAuth(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    logInState.value = LogInState.Success(it)

                },
                {
                    logInState.value = LogInState.Error("Uneli ste pogresne podatke")
                    Timber.e(it)

                },
                {
                    Timber.e("Completed")
                }
            )

        subscriptions.add(subscription)
        return true

    }
    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}