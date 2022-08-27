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

import timber.log.Timber

class LogInActivity : AppCompatActivity() {

    private val logInViewModel: LogInContract.LogInViewModel by viewModel<LogInViewModel>()
    private lateinit var binding: ActivityLogInBinding

    var username: String = ""
    private var password: String = ""
    private lateinit var save: SharedPreferences.Editor

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
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        save = sharedPreferences.edit()
        binding.btnlogin.setOnClickListener{
            username = binding.inputUsername.text.toString()
            password = binding.inputPassword.text.toString()


            if(username.isBlank() || password.isBlank()){
                Toast.makeText(applicationContext,"Polja ne mogu bit prazna!",Toast.LENGTH_SHORT).show()//ako polja nisu prazna saljemo serveru podatke
            }else {
                save.putBoolean("logged", true);
                save.putString("username", username)
                save.putString("password", password)
                logInViewModel.userAuth(username, password)
            }
        }
    }
    private fun initObservers(){
        logInViewModel.logInState.observe(this) {//kada pokupimo response sa servera startujemo activity
            Timber.e(it.toString())
            startMainActivity(it)
        }
    }

    private fun startMainActivity(state: LogInState) {
        when (state) {
            is LogInState.Success -> {
                Toast.makeText(this, "Successfully logged in", Toast.LENGTH_LONG).show()//cuvamo response
                save.putString("firstName", state.user.firstName)
                save.putString("lastName", state.user.lastName)
                save.putString("email", state.user.email)
                save.putString("pfp", state.user.image)
                save.putBoolean("rememberMe", binding.checkBox.isChecked)
                save.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            is LogInState.Error -> {
                Toast.makeText(this, "Wrong data entered", Toast.LENGTH_SHORT).show()
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

