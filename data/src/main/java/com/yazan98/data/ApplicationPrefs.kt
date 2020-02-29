package com.yazan98.data

import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.prefs.VortexPrefsConfig

object ApplicationPrefs {

    const val USERNAME = "username"
    const val TOKEN = "token"

    suspend fun saveUsername(username: String) {
        VortexPrefs.put(USERNAME, username)
    }

    suspend fun getUsername(): String {
        return VortexPrefs.get(USERNAME, "") as String
    }

    fun saveToken(token: String) {
        VortexPrefsConfig.prefs.edit().putString(TOKEN, token).apply()
    }

    fun getToken(): String {
        return VortexPrefsConfig.prefs.getString(TOKEN, "").toString()
    }

}