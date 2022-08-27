package com.example.projekat_avgust.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.projekat_avgust.databinding.FragmentCreationBinding
import com.example.projekat_avgust.presentation.view.recycler.adapter.TopNavPageAdapter
import com.google.android.material.tabs.TabLayout


class CreationFragment : Fragment(){

    private var _binding: FragmentCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {//ima init u on resume jer se nije cuvao view
        super.onResume()
        initView()
    }

    private fun initView(){//setupujemo tabove
        viewPager = binding.viewPagerTabs
        viewPager.offscreenPageLimit = 4
        tabLayout = binding.tabLayout

        viewPager.adapter = TopNavPageAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}