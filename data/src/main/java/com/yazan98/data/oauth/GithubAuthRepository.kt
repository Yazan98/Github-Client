package com.yazan98.data.oauth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.yazan98.data.ApplicationPrefs
import io.vortex.android.prefs.VortexPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class GithubAuthRepository(private val context: Context, private val listener: GithubAuthListener) {

    companion object {
        const val GITHUB_URL = "https://github.com/login/oauth/authorize"
        const val GITHUB_OAUTH = "https://github.com/login/oauth/access_token"
        var CODE = ""
        var CLIENT_ID = "Iv1.e5e4913aa69cf686"
        var CLIENT_SECRET = "12af868f3006b95eeefafd865e6eccb7115d452e"
    }

    fun getUrlChange(url : String?): Boolean {
        try {
            if (url?.contains("?code=") == false) return false
            CODE = url?.substring(url.lastIndexOf("?code=") + 1).toString()
            val token_code: List<String> = CODE.split("=")
            val tokenFetchedIs = token_code[1]
            val cleanToken =
                tokenFetchedIs.split("&".toRegex()).toTypedArray()
            fetchOauthTokenWithCode(cleanToken[0])

        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
        return false
    }

    fun getBaseUrl(): String {
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
                "delete_repo",
                "repo",
                "repo:status"
            )
        )
        urlLoader += "&scope=$scopeAppendToUrl"
        return urlLoader
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
                e.message?.let {
                    listener.onAuthError(it)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                if (response.isSuccessful()) {
                    response.body()?.string()?.let {
                        val JsonData: String = it

                        try {
                            val jsonObject = JSONObject(JsonData)
                            val auth_token = jsonObject.getString("access_token")
                            storeToSharedPreference(auth_token)
                            listener.onAuthFinished()
                        } catch (exp: JSONException) {
                            println("STATUS : json exception: " + exp.message)
                        }
                    }
                }
            }
        })
    }

    private fun storeToSharedPreference(auth_token: String) {
        ApplicationPrefs.saveToken(auth_token)
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


    object ResultCode {
        val SUCCESS = 1
        val ERROR = 2
    }
}