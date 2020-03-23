package com.yazan98.autohub.screen

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.autohub.starter.GithubStarter
import com.yazan98.autohub.utils.HomeViewPagerAdapter
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.screen_main.*
import kotlinx.coroutines.launch

class MainScreen : VortexScreen() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewpager?.apply {
            this.adapter = HomeViewPagerAdapter(supportFragmentManager)
        }

        MottomNavBar?.apply {
            this.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.HomeItem -> viewpager?.currentItem = 0
                    R.id.NotificationsButton -> viewpager?.currentItem = 1
                    R.id.ProfileButton -> viewpager?.currentItem = 2
                }
                true
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.screen_main
    }

}
