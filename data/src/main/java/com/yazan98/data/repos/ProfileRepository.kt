package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.ProfileApi
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable

class ProfileRepository : GithubRepository<ProfileApi>() , ProfileApi {

    private val service: ProfileApi by lazy {
        serviceProvider.create(ProfileApi::class.java)
    }

    override suspend fun getService(): ProfileApi {
        return serviceProvider.create(ProfileApi::class.java)
    }

    override fun getProfileInfoByToken(username: String): Flowable<GithubUser> {
        return service.getProfileInfoByToken(username)
    }

    override fun getFollowersByToken(username: String): Flowable<List<GithubUser>> {
        return service.getFollowersByToken(username)
    }

    override fun getFollowingByToken(username: String): Flowable<List<GithubUser>> {
        return service.getFollowingByToken(username)
    }

    override fun getReposByToken(username: String): Flowable<List<GithubRepositoryModel>> {
        return service.getReposByToken(username)
    }

}
