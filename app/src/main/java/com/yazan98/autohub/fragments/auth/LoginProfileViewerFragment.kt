package com.yazan98.autohub.fragments.auth

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.autohub.screen.MainScreen
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.utils.random.VortexImageLoaders
import kotlinx.android.synthetic.main.fragment_login_profile_viwer.*
import kotlinx.coroutines.launch

class LoginProfileViewerFragment : VortexBaseFragment() {

    override fun initScreen(view: View) {

        arguments?.let {
            it.getString("Image")?.also {
                ProfileImage?.apply {
                    VortexImageLoaders.loadLargeImageWithFresco(it, this, 400, 400)
                }
            }

            it.getString("Username")?.also {
                ProfileViewerUsername?.apply {
                    this.text = it
                }
            }

            it.getString("Bio")?.also {
                UsernameBio?.apply {
                    this.text = it
                }
            }
        }

        ContinueButton?.apply {
            this.setOnClickListener {
                lifecycleScope.launch {
                    VortexPrefs.saveUserStatus(true)
                    startScreen<MainScreen>(true)
                }
            }
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login_profile_viwer
    }
}