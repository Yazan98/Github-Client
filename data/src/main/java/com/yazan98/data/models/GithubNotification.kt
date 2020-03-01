package com.yazan98.data.models

data class GithubNotification(
    var unread: Boolean = false,
    var reason: String = "",
    var subject: GithubNotificationSubject,
    var repository: GithubRepositoryModel
)

data class GithubNotificationSubject(
    var title: String = "",
    var url: String = "",
    var type: String = ""
)
