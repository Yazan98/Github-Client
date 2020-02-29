package com.yazan98.autohub.starter

interface GithubStarterImpl {

    suspend fun startActions()

    suspend fun startSaveFollowings()

    suspend fun startSaveFollowers()

    suspend fun startSaveProfileInfo()

}
