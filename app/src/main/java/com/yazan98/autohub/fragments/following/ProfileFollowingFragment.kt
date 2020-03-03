package com.yazan98.autohub.fragments.following

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.utils.FollowingViewPagerAdapter
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.following_container_fragment.*

class ProfileFollowingFragment : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.following_container_fragment
    }

    override fun initScreen(view: View) {
        arguments?.let {
            it.getString("Name")?.also {
                ProfileTitle?.apply {
                    this.text = it
                }
            }
        }

        FollowingTabs?.apply {
            FollowViewPager?.let {
                this.setupWithViewPager(it)
                activity?.apply {
                    it.adapter = FollowingViewPagerAdapter(this.supportFragmentManager)
                }
            }
        }
    }
}