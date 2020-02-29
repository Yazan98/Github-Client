package com.yazan98.autohub.fragments

import android.content.Context
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.AutohubApplication
import com.yazan98.autohub.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragment : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initScreen(view: View) {
        (activity?.application as AutohubApplication).let {
            lifecycleScope.launch {
                it.startGithubActions()
            }
        }
    }

}
