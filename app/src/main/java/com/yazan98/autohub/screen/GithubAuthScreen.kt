package com.yazan98.autohub.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.oauth.GithubAuthListener
import com.yazan98.data.oauth.GithubAuthRepository
import com.yazan98.data.repos.ProfileRepository
import io.reactivex.disposables.CompositeDisposable
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.screen_github_auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class GithubAuthScreen : VortexScreen(), GithubAuthListener {

    private val compositeSubscription: CompositeDisposable by lazy { CompositeDisposable() }
    private val profileRepository: ProfileRepository by lazy {
        ProfileRepository()
    }

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
                        return repo.getUrlChange(url)
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

        lifecycleScope.launch(Dispatchers.IO) {
            compositeSubscription.add(profileRepository.getProfileInfoByToken().subscribe({
                lifecycleScope.launch {
                    ApplicationPrefs.saveUsername(it.name)
                }
            }, {
                it.message?.let {
                    Timber.d("The Response : Error : $it")
                }
            }))
        }

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

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }

}
