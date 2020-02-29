package com.yazan98.autohub.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.data.oauth.GithubAuthListener
import com.yazan98.data.oauth.GithubAuthRepository
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.screen_github_auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException


class GithubAuthScreen : VortexScreen(), GithubAuthListener {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GithubAuthRepository(this, this).let { repo ->
            GithubWebView?.apply {
                this.settings.javaScriptEnabled = true
                this.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        LoaderGithubWebView?.visibility = View.GONE
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        super.shouldOverrideUrlLoading(view, url)
                        return  repo.getUrlChange(url)
                    }

                }

                LoaderGithubWebView?.visibility = View.VISIBLE
                this.loadUrl(repo.getBaseUrl())

            }
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.screen_github_auth
    }

    override fun onAuthFinished() {
        lifecycleScope.launch(Dispatchers.Main) {
            VortexPrefs.saveUserStatus(true)
            Toast.makeText(this@GithubAuthScreen, "Login Successful", Toast.LENGTH_SHORT).show()
            startScreen<MainScreen>(true)
        }
    }

    override fun onAuthError(message: String) {
        lifecycleScope.launch {
            messageController.showSnackbar(this@GithubAuthScreen, message)
        }
    }

}
