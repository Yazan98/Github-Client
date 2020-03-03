package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.HomeScreenApi
import com.yazan98.data.models.GithubNotification
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
import io.vortex.android.models.VortexServiceProviderType

class HomeRepository : GithubRepository<HomeScreenApi>(), HomeScreenApi {

    override fun getServiceProvider(): HomeScreenApi {
        return getServiceProvider(VortexServiceProviderType.BASIC).create(HomeScreenApi::class.java)
    }

    override fun getNotifications(): Flowable<List<GithubNotification>> {
        return getServiceProvider().getNotifications()
    }

    override fun getStarredRepositories(): Flowable<List<GithubRepositoryModel>> {
        return getServiceProvider().getStarredRepositories()
    }

    override fun getProfileInfo(): Flowable<GithubUser> {
        return getServiceProvider().getProfileInfo()
    }

    override fun getRepositories(sort: String): Flowable<List<GithubRepositoryModel>> {
        return getServiceProvider().getRepositories()
    }

}
