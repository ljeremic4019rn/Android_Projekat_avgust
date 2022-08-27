package com.example.projekat_avgust.presentation.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat_avgust.R
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.FragmentEmployeesBinding
import com.example.projekat_avgust.presentation.contract.EmployeeContract
import com.example.projekat_avgust.presentation.view.activity.DetailedEmployeeActivity
import com.example.projekat_avgust.presentation.view.activity.UpdateEmployeeActivity
import com.example.projekat_avgust.presentation.view.recycler.adapter.EmployeeAdapter
import com.example.projekat_avgust.presentation.view.states.EmployeeState
import com.example.projekat_avgust.presentation.viewmodel.EmployeeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class EmployeeFragment : Fragment() {

    private val employeeViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()
    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EmployeeAdapter

    private var dataPull: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (dataPull){//ovo samo jednom radimo jer bi inace pregazilo sve promene, jer se na serveru ne menjaju podaci
            employeeViewModel.fetchAllEmployeesFromServer()
            dataPull = false
        }
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        binding.employeeRv.layoutManager = LinearLayoutManager(context)
        adapter = EmployeeAdapter(::openDetailed)//callback za on click
        binding.employeeRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.employeeRv.adapter = adapter


        binding.employeeRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    employeeViewModel.load10Employees(false)
                }
            }
        })
    }


    private fun openDetailed(employee: Employee){
        val builder = AlertDialog.Builder(activity,R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.options_dialog_box,null)

        val  cancelBtn = view.findViewById<Button>(R.id.cancelBtn)
        val  okBtn = view.findViewById<Button>(R.id.okBtn)
        val  radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupEmployees)

        builder.setView(view)

        cancelBtn.setOnClickListener {
            builder.dismiss()
        }

        okBtn.setOnClickListener {
            val radioButton =view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

            if (radioButton == null) {
                Toast.makeText(context, "Please select option", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when(radioButton.text.toString()){
                "Delete employee" -> employeeViewModel.deleteEmployee(employee.id)
                "Employee details" -> employeeViewModel.detailedEmployee(employee.id)
                "Update employee" -> startUpdateActivity(employee.id)
                else -> println("error")
            }
            builder.dismiss()
        }

        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun startUpdateActivity(id: Long) {
        val intent = Intent(activity, UpdateEmployeeActivity::class.java)
        intent.putExtra("id", id)
        doAction.launch(intent)
    }

    private val doAction: ActivityResultLauncher<Intent> = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data!!

            val id = data.getLongExtra("id",0)
            val newName = data.getStringExtra("name")!!
            val newSalary = data.getIntExtra("salary",0)
            val newAge = data.getIntExtra("age",0)

            employeeViewModel.updateEmployee(id,newName,newSalary,newAge)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {
        employeeViewModel.employeeState.observe(viewLifecycleOwner) { employeeState ->
            Timber.e(employeeState.toString())
            renderState(employeeState)
        }

        employeeViewModel.gradualRvList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        employeeViewModel.getAllEmployees()
    }


    private fun renderState(state: EmployeeState) {
        when (state) {
            is EmployeeState.Success -> {
//                adapter.submitList(state.employees)
                employeeViewModel.allEmployeesLocal = state.employees
                employeeViewModel.load10Employees(true)
            }
            is EmployeeState.Detailed -> {
                val intent = Intent(activity, DetailedEmployeeActivity::class.java)
                intent.putExtra("name", state.detailed.employee_name)
                intent.putExtra("salary", state.detailed.employee_salary)
                intent.putExtra("age", state.detailed.employee_age)
                startActivity(intent)
            }
            is EmployeeState.Deleted -> {
                Toast.makeText(context, "Deleted user with id: " + state.detailed, Toast.LENGTH_SHORT).show()
            }
            is EmployeeState.Created -> {
                Toast.makeText(context, "CREATED " + state.detailed, Toast.LENGTH_SHORT).show()
            }
            is EmployeeState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is EmployeeState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_SHORT).show()
            }
            is EmployeeState.Loading -> {
                println("Loading")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}