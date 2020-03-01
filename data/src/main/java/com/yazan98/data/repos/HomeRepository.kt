package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.HomeScreenApi
import com.yazan98.data.models.GithubNotification
import io.reactivex.Flowable
import io.vortex.android.models.VortexServiceProviderType

class HomeRepository : GithubRepository<HomeScreenApi>(), HomeScreenApi {

    override fun getServiceProvider(): HomeScreenApi {
        return getServiceProvider(VortexServiceProviderType.BASIC).create(HomeScreenApi::class.java)
    }

    override fun getNotifications(): Flowable<List<GithubNotification>> {
        return getServiceProvider().getNotifications()
    }

}
