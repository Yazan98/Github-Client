package com.yazan98.data.api

import com.yazan98.data.models.GithubNotification
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeScreenApi {

    @GET("notifications?all=true")
    fun getNotifications(): Flowable<List<GithubNotification>>

    @GET("user/starred")
    fun getStarredRepositories(): Flowable<List<GithubRepositoryModel>>

    @GET("user")
    fun getProfileInfo(): Flowable<GithubUser>

    @GET("user/repos?type=all")
    fun getRepositories(@Query("sort") sort: String = "updated"): Flowable<List<GithubRepositoryModel>>

}
