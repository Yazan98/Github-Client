package com.yazan98.autohub.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.coroutines.launch

class SplashScreen : VortexScreen() {

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            VortexPrefs.getUserStatus()?.also {
                when (it) {
                    true -> startScreen<MainScreen>(true)
                    false -> startScreen<RegisterScreen>(true)
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.screen_splash
    }
}
