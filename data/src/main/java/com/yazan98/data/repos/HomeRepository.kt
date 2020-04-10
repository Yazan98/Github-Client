package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.HomeScreenApi
import com.yazan98.data.models.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.vortex.android.models.VortexServiceProviderType

class HomeRepository : GithubRepository<HomeScreenApi>(), HomeScreenApi {

    override fun getServiceProvider(): HomeScreenApi {
        return getServiceProvider(VortexServiceProviderType.BASIC).create(HomeScreenApi::class.java)
    }

    override fun getNotifications(): Single<List<GithubNotification>> {
        return getServiceProvider().getNotifications()
    }

    override fun getStarredRepositories(): Single<List<GithubRepositoryModel>> {
        return getServiceProvider().getStarredRepositories()
    }

    override fun getProfileInfoByUsername(username: String): Single<GithubUser> {
        return getServiceProvider().getProfileInfoByUsername(username)
    }

    override fun getProfileInfo(): Single<GithubUser> {
        return getServiceProvider().getProfileInfo()
    }

    override fun getRepositories(sort: String): Single<List<GithubRepositoryModel>> {
        return getServiceProvider().getRepositories()
    }

    override fun getFeedsList(username: String): Single<List<FeedResponse>> {
        return getServiceProvider().getFeedsList(username)
    }

    override fun getOrgs(): Single<List<GithubOrg>> {
        return getServiceProvider().getOrgs()
    }

}
