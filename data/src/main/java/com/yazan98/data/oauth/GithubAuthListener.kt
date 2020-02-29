package com.yazan98.data.oauth

interface GithubAuthListener {

    fun onAuthFinished()

    fun onAuthError(message: String)

}