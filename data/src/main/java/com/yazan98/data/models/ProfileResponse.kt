package com.yazan98.data.models

data class ProfileResponse(
    var profile: GithubUser? = null,
    var repositories: List<GithubRepositoryModel>? = null
)