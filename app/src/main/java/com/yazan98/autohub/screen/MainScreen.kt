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

        MainFloatingButton?.apply {
            this.setOnClickListener {
                lifecycleScope.launch {
                    startScreen<ProfileScreen>(false)
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.screen_main
    }

}
