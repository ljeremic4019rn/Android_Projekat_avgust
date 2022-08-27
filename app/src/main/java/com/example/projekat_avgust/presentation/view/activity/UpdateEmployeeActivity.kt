package com.example.projekat_avgust.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projekat_avgust.R

class UpdateEmployeeActivity : AppCompatActivity() {

    private lateinit var nameET: EditText
    private lateinit var salaryET: EditText
    private lateinit var ageET: EditText
    private lateinit var submitBtn: Button
    private var name = ""
    private var salary = 0
    private var age = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_employee)

        init()
    }

    private fun init(){
        initView()
        initListeners()
    }


    private fun initView(){
        nameET = findViewById(R.id.nameCreate)
        salaryET = findViewById(R.id.salaryCreate)
        ageET = findViewById(R.id.ageCreate)
        submitBtn = findViewById(R.id.createBtn)

        ageET.setText("0")
        salaryET.setText("0")
    }

    private fun initListeners(){
        submitBtn.setOnClickListener {
            name =  nameET.text.toString()
            salary =  salaryET.text.toString().toInt()
            age = ageET.text.toString().toInt()

            if (name != "" && salary > 0 && age > 0){//ako su polja popunjena
                val returnIntent = Intent()
                returnIntent.putExtra("id", this.intent.getLongExtra("id", 0))
                returnIntent.putExtra("name",name)
                returnIntent.putExtra("salary",salary)
                returnIntent.putExtra("age", age)

                this.setResult(RESULT_OK, returnIntent)
                this.finish()
            }
            else Toast.makeText(this, "Please fill in all fields properly ", Toast.LENGTH_SHORT).show()


        }
    }
}