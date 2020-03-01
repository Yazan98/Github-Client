package com.yazan98.autohub.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yazan98.autohub.fragments.NotificationsFragment

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> NotificationsFragment()
            else -> NotificationsFragment()
        }
    }

    override fun getCount(): Int {
        return 1
    }
}