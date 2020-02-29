package com.yazan98.autohub.starter

class GithubStarter(private val username: String): GithubStarterImpl {

    override suspend fun startActions() {
        startSaveFollowers()
        startSaveFollowings()
        startSaveProfileInfo()
    }

    override suspend fun startSaveFollowings() {

    }

    override suspend fun startSaveFollowers() {

    }

    override suspend fun startSaveProfileInfo() {

    }

}
