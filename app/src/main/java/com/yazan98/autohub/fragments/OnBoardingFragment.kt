package com.yazan98.autohub.fragments

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.yazan98.autohub.R
import com.yazan98.autohub.screen.GithubAuthScreen
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_boarding.*
import kotlinx.coroutines.launch

class OnBoardingFragment : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_boarding
    }

    override fun initScreen(view: View) {
        GithubLogo?.apply {
            this.setActualImageResource(R.drawable.ic_cat)
        }

        LoginAuthButton?.apply {
            this.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_authFragment)
            }
        }

        LoginButton?.apply {
            this.setOnClickListener {
                lifecycleScope.launch {
                    startScreen<GithubAuthScreen>(true)
                }
            }
        }
    }
}