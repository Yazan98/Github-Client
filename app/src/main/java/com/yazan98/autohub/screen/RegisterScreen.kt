package com.yazan98.autohub.screen

import android.content.Intent
import android.os.Bundle
import com.yazan98.autohub.R
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.screen_reg.*

class RegisterScreen : VortexScreen() {

    override fun getLayoutRes(): Int {
        return R.layout.screen_reg
    }
}