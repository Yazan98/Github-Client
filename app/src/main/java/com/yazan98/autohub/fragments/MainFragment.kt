package com.yazan98.autohub.fragments

import android.content.Context
import android.view.View
import com.yazan98.autohub.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

class MainFragment : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initScreen(view: View) {
        val prefs = activity?.getSharedPreferences("github_prefs", Context.MODE_PRIVATE)
        Token.setText(prefs?.getString("oauth_token", ""))
        Timber.d("Tokennn : ${prefs?.getString("oauth_token", "")}")
    }

}
