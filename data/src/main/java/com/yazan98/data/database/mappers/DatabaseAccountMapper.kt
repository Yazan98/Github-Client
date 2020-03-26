package com.yazan98.data.database.mappers

import com.yazan98.data.database.DatabaseMapper
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.models.GithubUser

class DatabaseAccountMapper : DatabaseMapper<GithubUser, GithubAccount> {

    override fun get(from: GithubUser): GithubAccount {
        val user = GithubAccount()
        user.id = from.id
//        user.avatar_url = from.avatar_url
        user.entityType = "Following"
        user.login = from.login
        return user
    }

}
