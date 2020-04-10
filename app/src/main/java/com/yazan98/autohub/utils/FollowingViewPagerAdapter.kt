package com.yazan98.autohub.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yazan98.autohub.fragments.profile.FollowersFragment
import com.yazan98.autohub.fragments.profile.FollowingFragment

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

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Following"
            1 -> "Followers"
            else -> "Following"
        }
    }

}