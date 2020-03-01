package com.yazan98.autohub.adapters.listeners

import com.yazan98.data.models.GithubRepositoryModel

interface NotificationListener {

    fun onNotificationSelected(repo: GithubRepositoryModel)

}