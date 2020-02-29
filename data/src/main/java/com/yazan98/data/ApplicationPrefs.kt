package com.yazan98.data

import io.vortex.android.prefs.VortexPrefs

object ApplicationPrefs {

    const val USERNAME = "username"

    suspend fun saveUsername(username: String) {
        VortexPrefs.put(USERNAME, username)
    }

    suspend fun getUsername(): String {
        return VortexPrefs.get(USERNAME, "") as String
    }

}