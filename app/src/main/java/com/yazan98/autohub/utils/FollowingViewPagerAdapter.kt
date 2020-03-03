package com.yazan98.autohub.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yazan98.autohub.fragments.following.FollowersFragment
import com.yazan98.autohub.fragments.following.FollowingFragment

class FollowingViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FollowingFragment()
            1 -> FollowersFragment()
            else -> FollowingFragment()
        }
    }

}