package com.yazan98.data

import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.prefs.VortexPrefsConfig

object ApplicationPrefs {

    const val USERNAME = "username"
    const val TOKEN = "token"
    const val PASSWORD = "pass"
    const val REPO_NAME = "repo"
    const val PROFILE_SELECTED_NAME = "profile"

    suspend fun saveUsername(username: String) {
        VortexPrefs.put(USERNAME, username)
    }

    fun getUsername(): String {
        return VortexPrefsConfig.prefs.getString(USERNAME, "").toString()
    }

    fun saveToken(token: String) {
        VortexPrefsConfig.prefs.edit().putString(TOKEN, token).apply()
    }

    fun getToken(): String {
        return VortexPrefsConfig.prefs.getString(TOKEN, "").toString()
    }

    fun getPassword(): String {
        return VortexPrefsConfig.prefs.getString(PASSWORD, "").toString()
    }

    fun savePassword(password: String) {
        VortexPrefsConfig.prefs.edit().putString(PASSWORD, password).apply()
    }


    fun getSelectedRepo(): String {
        return VortexPrefsConfig.prefs.getString(REPO_NAME, "").toString()
    }

    fun saveSelectedRepo(password: String) {
        VortexPrefsConfig.prefs.edit().putString(REPO_NAME, password).apply()
    }

    fun getSelectedUsername(): String {
        return VortexPrefsConfig.prefs.getString(PROFILE_SELECTED_NAME, "").toString()
    }

    fun saveSelectedUsername(password: String) {
        VortexPrefsConfig.prefs.edit().putString(PROFILE_SELECTED_NAME, password).apply()
    }

}