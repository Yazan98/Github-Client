package com.yazan98.data.database.mappers

import com.yazan98.data.database.DatabaseMapper
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.models.GithubUser

class DatabaseAccountMapper : DatabaseMapper<GithubUser, GithubAccount> {

    override fun get(from: GithubUser): GithubAccount {
        val user = GithubAccount()
        user.avatar_url = from.avatar_url
        user.bio = from.bio
        user.blog = from.blog
        user.company = from.company
        user.created_at = from.created_at
        user.entityType = "Following"
        user.email = from.email
        user.followers = from.followers
        user.following = from.following
        user.hireable = from.hireable
        user.location = from.location
        user.login = from.login
        user.name = from.name
        user.public_gists = from.public_gists
        user.public_repos = from.public_repos
        return user
    }

}