package com.yazan98.data.models

data class GithubRepositoryModel(
    var name: String = "",
    var full_name: String = "",
    var private: Boolean = false,
    var owner: GithubUser,
    var description: String = "",
    var url: String = "",
    var homepage: String = "",
    var created_at: String = "",
    var updated_at: String = "",
    var language: String? = "",
    var watchers_count: Long = 0,
    var has_issues: Boolean = false,
    var has_projects: Boolean = false,
    var open_issues_count: Long = 0,
    var license: GithubProjectLicense?,
    var forks: Long = 0,
    var watchers: Long = 0,
    var default_branch: String = "",
    var archived: Boolean = false
)