package com.yazan98.data.api

import com.yazan98.data.models.*
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("repos/{username}/{RepoName}/commits")
    fun getRepositoryCommits(
        @Path("username") username: String,
        @Path("RepoName") repoName: String
    ): Single<List<GithubCommit>>

    @GET("repos/{username}/{RepoName}/topics")
    fun getRepositoryTopics(
        @Path("username") username: String,
        @Path("RepoName") repoName: String,
        @Header("Accept") acceptHeader: String = "application/vnd.github.mercy-preview+json"
    ): Single<GithubTopicResponse>

    @GET("repos/{username}/{RepoName}/contributors")
    fun getRepositoryContributors(
        @Path("username") username: String,
        @Path("RepoName") repoName: String,
        @Header("Accept") acceptHeader: String = "application/vnd.github.mercy-preview+json"
    ): Single<List<GithubUser>>



}