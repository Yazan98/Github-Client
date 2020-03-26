package com.yazan98.autohub.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yazan98.autohub.fragments.repo.CommitsFragment
import com.yazan98.autohub.fragments.repo.ContributorsFragment
import com.yazan98.autohub.fragments.repo.RepositoryInfoFragment

class RepositoryFragmentsAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> RepositoryInfoFragment()
            1 -> CommitsFragment()
            2 -> ContributorsFragment()
            else -> RepositoryInfoFragment()
        }
    }
}
