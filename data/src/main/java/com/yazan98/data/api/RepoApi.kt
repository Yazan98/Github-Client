package com.yazan98.data.api

import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubRepositoryReadme
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApi {

    @GET("repos/{username}/{RepoName}")
    fun getRepository(
        @Path("username") username: String,
        @Path("RepoName") repoName: String
    ): Flowable<GithubRepositoryModel>

    @GET("repos/{username}/{RepoName}/readme")
    fun getRepositoryReadmeFile(
        @Path("username") username: String,
        @Path("RepoName") repoName: String
    ): Flowable<GithubRepositoryReadme>

}