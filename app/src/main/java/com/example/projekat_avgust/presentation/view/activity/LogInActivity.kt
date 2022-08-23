package com.example.projekat_avgust.presentation.view.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projekat_avgust.databinding.ActivityLogInBinding
import com.example.projekat_avgust.presentation.contract.LogInContract
import com.example.projekat_avgust.presentation.view.states.LogInState
import com.example.projekat_avgust.presentation.viewmodel.LogInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.adapter.rxjava2.HttpException

import timber.log.Timber
import java.util.*

class LogInActivity : AppCompatActivity() {

    private val logInViewModel: LogInContract.LogInViewModel by viewModel<LogInViewModel>()

    private lateinit var binding: ActivityLogInBinding
    var username: String = ""
    var password: String = ""
    lateinit var save: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        initView()
        initObservers()

    }

    private fun initView(){
        val sharedPreferences = getSharedPreferences("ulogovan", MODE_PRIVATE);
        save = sharedPreferences.edit()
        binding.btnlogin.setOnClickListener{
            username = binding.inputUsername.text.toString()
            password = binding.inputPassword.text.toString()
            if(username.isBlank() || password.isBlank()){
                Toast.makeText(applicationContext,"Polja ne mogu bit prazna!",Toast.LENGTH_SHORT).show()
            }else {
                save.putBoolean("logged", true);
                save.putString("username", username.toString())
                save.putString("password", password.toString())
                logInViewModel.userAuth(username, password)
            }

        }
//            finish()


        }
    private fun initObservers(){
        logInViewModel.logInState.observe(this) {
            Timber.e(it.toString())
            renderState(it)

        }
    }

    private fun initListeners(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun renderState(state: LogInState) {
        when (state) {
            is LogInState.Success -> {
                Toast.makeText(this, "Uspesno ste se prijavili", Toast.LENGTH_LONG).show()
                save.putString("firstName", state.user.firstName)
                save.putString("lastName", state.user.lastName)
                save.putString("gender", state.user.gender)
                save.putString("slika", state.user.image)
                save.apply()

                initListeners()
            }
            is LogInState.Error -> {
                Toast.makeText(this, "Uneli ste pogresne podatke u activity", Toast.LENGTH_SHORT).show()
            }
            is LogInState.DataFetched -> {
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
            }
            is LogInState.Loading -> {
            }
        }
    }

    }

