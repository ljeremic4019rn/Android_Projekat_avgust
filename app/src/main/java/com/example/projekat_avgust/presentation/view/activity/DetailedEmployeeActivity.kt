package com.example.projekat_avgust.presentation.view.activity

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

    private fun initView(){//todo prosledi podatke ovde
        binding.emplName.text = intent.getStringExtra("name")
        binding.emplSalary.text = intent.getStringExtra("salary")
        binding.emplAge.text  = intent.getStringExtra("age")

        if (intent.getStringExtra("salary")?.toInt()!! >= 100000)
            binding.emplSalary.setTextColor(Color.parseColor("#1ee832"))
        else binding.emplSalary.setTextColor(Color.parseColor("#ff1100"))
    }
}