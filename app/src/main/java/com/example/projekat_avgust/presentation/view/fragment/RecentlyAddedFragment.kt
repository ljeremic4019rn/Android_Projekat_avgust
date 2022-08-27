package com.example.projekat_avgust.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat_avgust.data.models.Employee
import com.example.projekat_avgust.databinding.RecentlyAddedFragmentBinding
import com.example.projekat_avgust.presentation.contract.EmployeeContract
import com.example.projekat_avgust.presentation.view.recycler.adapter.EmployeeAdapter
import com.example.projekat_avgust.presentation.viewmodel.EmployeeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecentlyAddedFragment : Fragment(){

    private val employeeViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()
    private var _binding: RecentlyAddedFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EmployeeAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecentlyAddedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    override fun onResume() {
//        super.onResume()
//        adapter.notifyDataSetChanged()
//    }

    private fun init(){
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        binding.recentlyAddedRv.layoutManager = LinearLayoutManager(context)
        adapter = EmployeeAdapter(::openDetailed)
        binding.recentlyAddedRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.recentlyAddedRv.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {
        employeeViewModel.newEmployees.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()//notifikator da bi se promenio recycler nakon 2 min
        }
    }

    private fun openDetailed(employee: Employee){//mrzelo me da pravim nove adaptere tako da koristim isti kao za main recycler
        println("click")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}