package com.yazan98.data.models

data class GithubUser(
    var login: String = "",
    var avatar_url: String = "",
    var url: String = "",
    var type: String = "",
    var name: String = "",
    var company: String = "",
    var blog: String? = null,
    var location: String = "",
    var email: String? = null,
    var hireable: Boolean = false,
    var bio: String = "",
    var public_repos: Long = 0,
    var public_gists: Long = 0,
    var followers: Long = 0,
    var following: Long,
    var created_at: String = "",
    var updated_at: String = ""
)