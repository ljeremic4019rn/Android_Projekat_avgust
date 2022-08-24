package com.example.projekat_avgust.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.projekat_avgust.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
            val logged = sharedPreferences.getBoolean("rememberMe", false)

            if(logged) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }

            finish()
            false
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
    }
