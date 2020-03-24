package com.yazan98.data.models

data class FeedResponse(
    var id: Long = 0,
    var type: String = "",
    var actor: GithubUser,
    var repo: GithubRepositoryModel,
    var payload:FeedPayload,
    var public: Boolean = true,
    var created_at: String = ""
)

data class FeedPayload(
    var action: String = ""
)