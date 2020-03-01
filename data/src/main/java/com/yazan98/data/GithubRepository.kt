package com.yazan98.data

import io.vortex.android.data.VortexRepository
import io.vortex.android.models.VortexAuth
import io.vortex.android.models.VortexRequestDetailsProvider
import io.vortex.android.models.VortexServiceProviderType
import retrofit2.Retrofit

abstract class GithubRepository<Api> : VortexRepository<Api>() {

    private val retrofitFactory: RepositoryComponent by lazy {
        RepositoryComponentImpl()
    }

    override fun getBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun getRequestDetails(): VortexRequestDetailsProvider {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBasicAuthConfiguration(details: VortexAuth): String {
        return super.getBasicAuthConfiguration(details)
    }

    override fun getServiceProvider(type: VortexServiceProviderType): Retrofit {
        return retrofitFactory.getRetrofitConfiguration()
    }

    override suspend fun getService(): Api {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun getServiceProvider(): Api
}