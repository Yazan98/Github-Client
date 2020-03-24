package com.yazan98.autohub.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yazan98.autohub.fragments.main.FeedFragment
import com.yazan98.autohub.fragments.main.NotificationsFragment
import com.yazan98.autohub.fragments.main.ProfileFragment

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FeedFragment()
            1 -> NotificationsFragment()
            2 -> ProfileFragment()
            else -> NotificationsFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

}
