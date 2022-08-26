package com.example.projekat_avgust.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.AddEmployeeFragmentBinding
import com.example.projekat_avgust.presentation.contract.EmployeeContract
import com.example.projekat_avgust.presentation.viewmodel.EmployeeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateEmployeeFragment : Fragment(){

    private var _binding: AddEmployeeFragmentBinding? = null
    private val binding get() = _binding!!

    private val employeeViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()
    private lateinit var nameET: EditText
    private lateinit var salaryET: EditText
    private lateinit var ageET: EditText
    private lateinit var submitBtn: Button
    private var name = ""
    private var salary = 0
    private var age = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddEmployeeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initView()
        initListeners()
    }

    private fun initView(){
        nameET = binding.nameCreate
        salaryET = binding.salaryCreate
        ageET = binding.ageCreate
        submitBtn = binding.createBtn

        ageET.setText("0")
        salaryET.setText("0")
    }

    private fun initListeners(){
        submitBtn.setOnClickListener {
            name =  nameET.text.toString()
            salary =  salaryET.text.toString().toInt()
            age = ageET.text.toString().toInt()

            if (name != "" && salary > 0 && age > 0){
                employeeViewModel.addNewEmployee(Employee(0, name, salary.toString(), age.toString(), ""))

                nameET.setText("")
                ageET.setText("0")
                salaryET.setText("0")
            }
            else Toast.makeText(activity, "Please fill in all fields properly ", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}