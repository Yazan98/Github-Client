package com.yazan98.data.models

import android.provider.ContactsContract

data class ProfileResponse(
    var profile: GithubUser? = null,
    var repositories: List<GithubRepositoryModel>? = null,
    var organizations : List<GithubOrg>? = null
)