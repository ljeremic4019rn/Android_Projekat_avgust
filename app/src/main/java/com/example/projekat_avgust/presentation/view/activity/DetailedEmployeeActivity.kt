package com.example.projekat_avgust.presentation.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projekat_avgust.databinding.ActivityDetailedEmployeeBinding
import android.graphics.Color


class DetailedEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){//prikazujemo date podatke
        binding.emplName.text = "Name: " + intent.getStringExtra("name")
        binding.emplSalary.text = "Salary: " +  intent.getStringExtra("salary")
        binding.emplAge.text  = "Age: " +  intent.getStringExtra("age")

        if (intent.getStringExtra("salary")?.toInt()!! >= 100000)
            binding.emplSalary.setTextColor(Color.parseColor("#1ee832"))
        else binding.emplSalary.setTextColor(Color.parseColor("#ff1100"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}