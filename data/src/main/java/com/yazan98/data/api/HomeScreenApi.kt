package com.yazan98.data.api

import com.yazan98.data.models.GithubNotification
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
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

}
