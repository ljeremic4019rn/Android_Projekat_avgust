package com.example.projekat_avgust.presentation.view.recycler.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.projekat_avgust.presentation.view.fragment.CreateEmployeeFragment
import com.example.projekat_avgust.presentation.view.fragment.RecentlyAddedFragment

class TopNavPageAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm){

    companion object {
        const val ITEM_COUNT = 2
        const val FRAG_1_CREATE = 0
        const val FRAG_2_RECENTLY_ADDED = 1
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            FRAG_1_CREATE -> CreateEmployeeFragment()
            FRAG_2_RECENTLY_ADDED -> RecentlyAddedFragment()
            else -> throw IllegalStateException("Unexpected value: $position")
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            FRAG_1_CREATE -> "Create employee"
            FRAG_2_RECENTLY_ADDED -> "Recently added"
            else -> "error"
        }
    }

}