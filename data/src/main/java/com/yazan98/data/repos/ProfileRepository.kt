package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.ProfileApi
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.vortex.android.models.VortexServiceProviderType

class ProfileRepository : GithubRepository<ProfileApi>() , ProfileApi {

    private val service: ProfileApi by lazy {
        getServiceProvider(VortexServiceProviderType.REACTIVE_FULL).create(ProfileApi::class.java)
    }

    override fun getServiceProvider(): ProfileApi {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getService(): ProfileApi {
        return serviceProvider.create(ProfileApi::class.java)
    }

    override fun getProfileInfoByUsername(username: String): Single<GithubUser> {
        return service.getProfileInfoByUsername(username)
    }

    override fun getProfileInfoByToken(): Single<GithubUser> {
        return service.getProfileInfoByToken()
    }

    override fun getFollowersByToken(username: String): Single<List<GithubUser>> {
        return service.getFollowersByToken(username)
    }

    override fun getFollowersByToken(): Single<List<GithubUser>> {
        return service.getFollowersByToken()
    }

    override fun getReposByToken(username: String): Single<List<GithubRepositoryModel>> {
        return service.getReposByToken(username)
    }

    override fun getFollowingByToken(): Single<List<GithubUser>> {
        return service.getFollowingByToken()
    }
}
