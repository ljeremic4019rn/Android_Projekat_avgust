package com.example.projekat_avgust.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projekat_avgust.R
import com.example.projekat_avgust.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {//setupujemo drawer navigaciju
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_users, R.id.nav_create_user), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        menuInflater.inflate(R.menu.main, menu)

        val email = findViewById<TextView>(R.id.boxEmail)
        email.text = sharedPreferences.getString("email","server error")//setujemo podatke u drawer navigatoru
        Picasso
            .get()
            .load(sharedPreferences.getString("pfp",""))
            .into(findViewById<ImageView>(R.id.boxPfp))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {//stavljamo onclick za gornje desno logout dugme
        R.id.logoutBtn -> {
            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)

            sharedPreferences
                .edit()
                .putBoolean("rememberMe", false)
                .apply()

            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        else -> false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}