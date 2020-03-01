package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.ProfileApi
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
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

    override fun getProfileInfoByUsername(username: String): Flowable<GithubUser> {
        return service.getProfileInfoByUsername(username)
    }

    override fun getProfileInfoByToken(): Flowable<GithubUser> {
        return service.getProfileInfoByToken()
    }

    override fun getFollowersByToken(username: String): Flowable<List<GithubUser>> {
        return service.getFollowersByToken(username)
    }

    override fun getFollowingByToken(): Flowable<List<GithubUser>> {
        return service.getFollowingByToken()
    }

    override fun getReposByToken(username: String): Flowable<List<GithubRepositoryModel>> {
        return service.getReposByToken(username)
    }

}
