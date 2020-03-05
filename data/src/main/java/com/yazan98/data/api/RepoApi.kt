package com.yazan98.data.api

import com.yazan98.data.models.GithubRepositoryModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApi {

    @GET("repos/{username}/{RepoName}")
    fun getRepository(
        @Path("username") username: String,
        @Path("RepoName") repoName: String
    ): Flowable<GithubRepositoryModel>

}