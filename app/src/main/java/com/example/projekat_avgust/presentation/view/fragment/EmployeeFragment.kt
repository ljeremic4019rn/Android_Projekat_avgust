package com.example.projekat_avgust.presentation.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat_avgust.R
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.FragmentEmployeesBinding
import com.example.projekat_avgust.presentation.contract.EmployeeContract
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
    val alertDialogBuilder = AlertDialog.Builder(activity)



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


    private fun initRecycler() {
        binding.employeeRv.layoutManager = LinearLayoutManager(context)
        adapter = EmployeeAdapter(::openDetailed)//callback za on click
        binding.employeeRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.employeeRv.adapter = adapter
    }

    private fun openDetailed(employee: Employee){//todo dialog box
//        val builder = AlertDialog.Builder(activity, R.style.Option).create()




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
            is EmployeeState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is EmployeeState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
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