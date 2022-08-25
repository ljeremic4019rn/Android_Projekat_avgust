package com.example.projekat_avgust.presentation.view.fragment

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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat_avgust.R
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.FragmentEmployeesBinding
import com.example.projekat_avgust.presentation.contract.EmployeeContract
import com.example.projekat_avgust.presentation.view.activity.DetailedEmployeeActivity
import com.example.projekat_avgust.presentation.view.activity.MainActivity
import com.example.projekat_avgust.presentation.view.recycler.adapter.EmployeeAdapter
import com.example.projekat_avgust.presentation.view.states.EmployeeState
import com.example.projekat_avgust.presentation.viewmodel.EmployeeViewModel
import kotlinx.android.synthetic.main.activity_log_in.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class EmployeeFragment : Fragment() {

    private val employeeViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()
    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EmployeeAdapter



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
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {//todo PAGINACIJA
        binding.employeeRv.layoutManager = LinearLayoutManager(context)
        adapter = EmployeeAdapter(::openDetailed)//callback za on click
        binding.employeeRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.employeeRv.adapter = adapter
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

            when(radioButton.text.toString()){//todo dodaj funkcionalnost
                "Delete employee" -> println("delete")// employeeViewModel.deleteEmployee()
                "Update employee" -> println("update")//employeeViewModel.updateEmployee()
                "Employee details" -> employeeViewModel.detailedEmployee(employee.id)
            }
            builder.dismiss()
        }

        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun initObservers() {
        employeeViewModel.employeeState.observe(viewLifecycleOwner) { employeeState ->
            Timber.e(employeeState.toString())
            renderState(employeeState)
        }

        employeeViewModel.fetchAllEmployeesFromServer()
        employeeViewModel.getAllEmployees()
    }


    private fun renderState(state: EmployeeState) {
        when (state) {
            is EmployeeState.Success -> {
                adapter.submitList(state.employees)
            }
            is EmployeeState.Detailed -> {
                val intent = Intent(activity, DetailedEmployeeActivity::class.java)
                intent.putExtra("name", state.detailed.employee_name)
                intent.putExtra("salary", state.detailed.employee_salary)
                intent.putExtra("age", state.detailed.employee_age)
                startActivity(intent)
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