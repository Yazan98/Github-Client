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


class GithubAuthScreen : VortexScreen() {

    companion object {
        const val GITHUB_URL = "https://github.com/login/oauth/authorize"
        const val GITHUB_OAUTH = "https://github.com/login/oauth/access_token"
        var CODE = ""
        var PACKAGE = ""
        var CLIENT_ID = "Iv1.e5e4913aa69cf686"
        var CLIENT_SECRET = "12af868f3006b95eeefafd865e6eccb7115d452e"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GithubWebView?.apply {
            this.settings.javaScriptEnabled = true
            this.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    LoaderGithubWebView?.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    super.shouldOverrideUrlLoading(view, url)
                    // Try catch to allow in app browsing without crashing.
                    // Try catch to allow in app browsing without crashing.
                    Timber.d("STATUS : Changed")
                    Timber.d("STATUS : Changed Url : $url")
                    Timber.d("STATUS : Changed Webview : Url : ${view?.url}")
                    Timber.d("STATUS : ${view?.url?.contains("?code=")}")
                    try {
                        if (url?.contains("?code=") == false) return false
                        CODE = url?.substring(url.lastIndexOf("?code=") + 1).toString()
                        val token_code: List<String> = CODE.split("=")
                        val tokenFetchedIs = token_code[1]
                        val cleanToken =
                            tokenFetchedIs.split("&".toRegex()).toTypedArray()
                        fetchOauthTokenWithCode(cleanToken[0])
                        Timber.d("STATUS : code fetched is: $CODE")
                        Timber.d("STATUS : code token: " + token_code[1])
                        Timber.d("STATUS : token cleaned is: " + cleanToken[0])

                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                    return false
                }

            }

            var urlLoader = "$GITHUB_URL?client_id=$CLIENT_ID"
            val scopeAppendToUrl = getCsvFromList(
                arrayListOf(
                    "repo:status",
                    "repo_deployment",
                    "public_repo",
                    "admin:org",
                    "write:org",
                    "read:org",
                    "admin:public_key",
                    "write:public_key",
                    "read:public_key",
                    "admin:repo_hook",
                    "write:repo_hook",
                    "read:repo_hook",
                    "admin:org_hook",
                    "gist",
                    "notifications",
                    "user",
                    "read:user",
                    "user:email",
                    "user:follow",
                    "delete_repo"
                )
            )
            urlLoader += "&scope=$scopeAppendToUrl"
            Timber.d("STATUS : BaseUrl : $urlLoader")
            LoaderGithubWebView?.visibility = View.VISIBLE
            this.loadUrl(urlLoader)

        }
    }

    private fun fetchOauthTokenWithCode(code: String) {
        val client = OkHttpClient()
        val url = HttpUrl.parse(GITHUB_OAUTH)!!.newBuilder()
        url.addQueryParameter("client_id", CLIENT_ID)
        url.addQueryParameter("client_secret", CLIENT_SECRET)
        url.addQueryParameter("code", code)
        val url_oauth = url.build().toString()
        val request: Request = Request.Builder()
            .header("Accept", "application/json")
            .url(url_oauth)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                Timber.d("STATUS : IOException: " + e.message)
                finishThisActivity(ResultCode.ERROR)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                if (response.isSuccessful()) {
                    response.body()?.string()?.let {
                        val JsonData: String = it
                        Timber.d(
                            "STATUS : response is: $JsonData"
                        )
                        try {
                            val jsonObject = JSONObject(JsonData)
                            val auth_token = jsonObject.getString("access_token")
                            storeToSharedPreference(auth_token)
                            Timber.d(
                                "STATUS : token is: $auth_token"
                            )
                            lifecycleScope.launch(Dispatchers.Main) {
                                VortexPrefs.saveUserStatus(true)
                                Toast.makeText(this@GithubAuthScreen, "Login Successful", Toast.LENGTH_SHORT).show()
                                startScreen<MainScreen>(true)
                            }

                        } catch (exp: JSONException) {
                            Timber.d(
                                "STATUS : json exception: " + exp.message
                            )
                        }
                    }
                    finishThisActivity(ResultCode.SUCCESS)
                }
            }
        })
    }

//    override fun onBackPressed() {
//        if (GithubWebView.canGoBack()) {
//            GithubWebView?.goBack()
//        } else {
//            super.onBackPressed()
//        }
//    }

    private fun storeToSharedPreference(auth_token: String) {
        val prefs =
            getSharedPreferences("github_prefs", Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString("oauth_token", auth_token)
        edit.apply()
    }

    /**
     * Finish this activity and returns the result
     *
     * @param resultCode one of the constants from the class ResultCode
     */
    private fun finishThisActivity(resultCode: Int) {
        setResult(resultCode)
        finish()
    }


    private fun getCsvFromList(scopeList: List<String>): String? {
        var csvString = ""
        for (scope in scopeList) {
            if (csvString != "") {
                csvString += ","
            }
            csvString += scope
        }
        return csvString
    }

    override fun getLayoutRes(): Int {
        return R.layout.screen_github_auth
    }

    object ResultCode {
        val SUCCESS = 1
        val ERROR = 2
    }

}
