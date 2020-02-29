package com.yazan98.autohub.screen

import android.content.Intent
import android.os.Bundle
import com.yazan98.autohub.R
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.screen_reg.*

class RegisterScreen : VortexScreen() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GithubLogo?.apply {
            this.setActualImageResource(R.drawable.ic_cat)
        }

        LoginButton?.apply {
            this.setOnClickListener {
                Intent(this@RegisterScreen, GithubAuthScreen::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.screen_reg
    }
}