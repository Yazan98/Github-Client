package com.yazan98.data.repos

import com.yazan98.data.GithubRepository
import com.yazan98.data.api.RepoApi
import com.yazan98.data.models.GithubCommit
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubRepositoryReadme
import com.yazan98.data.models.GithubTopicResponse
import io.reactivex.Flowable
import io.reactivex.Single
import io.vortex.android.models.VortexServiceProviderType
import javax.inject.Inject

class RepoRepository @Inject constructor(): GithubRepository<RepoApi>(), RepoApi {

    override fun getServiceProvider(): RepoApi {
        return getServiceProvider(VortexServiceProviderType.BASIC).create(RepoApi::class.java)
    }

    override fun getRepository(username: String, repoName: String): Flowable<GithubRepositoryModel> {
        return getServiceProvider().getRepository(username, repoName)
    }

    override fun getRepositoryReadmeFile(username: String, repoName: String): Flowable<GithubRepositoryReadme> {
        return getServiceProvider().getRepositoryReadmeFile(username, repoName)
    }

    override fun getRepositoryCommits(
        username: String,
        repoName: String
    ): Single<List<GithubCommit>> {
        return getServiceProvider().getRepositoryCommits(username, repoName)
    }

    override fun getRepositoryTopics(
        username: String,
        repoName: String,
        acceptHeader: String
    ): Single<GithubTopicResponse> {
        return getServiceProvider().getRepositoryTopics(username, repoName)
    }

}
