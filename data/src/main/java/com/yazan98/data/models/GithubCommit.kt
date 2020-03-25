package com.yazan98.data.models

data class GithubCommit(
    var node_id: String = "",
    var commit: CommitBody,
    var author: GithubUser
)

data class CommitBody(
    val author: CommitUser,
    val committer: CommitUser,
    var message: String = "",
    var comment_count: Int = 0
)

data class CommitUser(
    var name: String = "",
    var email: String = "",
    var date: String = ""
)