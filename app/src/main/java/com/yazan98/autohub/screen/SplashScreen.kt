package com.yazan98.autohub.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yazan98.autohub.R
import io.vortex.android.ui.activity.VortexScreen

class SplashScreen : VortexScreen() {
    override fun getLayoutRes(): Int {
        return R.layout.screen_splash
    }
}
