package com.yazan98.data.api

import com.yazan98.data.models.*
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeScreenApi {

    @GET("notifications?all=true")
    fun getNotifications(): Single<List<GithubNotification>>

    @GET("user/starred")
    fun getStarredRepositories(): Single<List<GithubRepositoryModel>>

    @GET("user")
    fun getProfileInfo(): Single<GithubUser>

    @GET("user/repos?type=all")
    fun getRepositories(@Query("sort") sort: String = "updated"): Single<List<GithubRepositoryModel>>

    @GET("users/{username}/received_events?type=all")
    fun getFeedsList(@Path("username") username: String): Single<List<FeedResponse>>

    @GET("user/orgs")
    fun getOrgs(): Single<List<GithubOrg>>

}
