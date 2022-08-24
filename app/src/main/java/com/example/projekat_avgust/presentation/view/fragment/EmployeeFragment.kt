package com.example.projekat_avgust.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.projekat_avgust.databinding.FragmentEmployeesBinding
import com.example.projekat_avgust.presentation.contract.EmployeeContract
import com.example.projekat_avgust.presentation.view.states.EmployeeState
import com.example.projekat_avgust.presentation.viewmodel.EmployeeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class EmployeeFragment : Fragment() {

    private val employeeViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()

    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
//        initRecycler()
//        initListeners()
        initObservers()
    }



    private fun initListeners() {
        TODO("Not yet implemented")
    }

    private fun initRecycler() {
        TODO("Not yet implemented")
    }

    private fun initObservers() {
        employeeViewModel.employeeState.observe(viewLifecycleOwner, Observer { employeeState ->
            Timber.e(employeeState.toString())
            renderState(employeeState)
        })


        employeeViewModel.fetchAllEmployeesFromServer()
//        employeeViewModel.getAllEmployees() todo get from db
    }


    private fun renderState(state: EmployeeState) {
        when (state) {
            is EmployeeState.Success -> {
//                adapter.submitList(state.employees) todo recycler
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