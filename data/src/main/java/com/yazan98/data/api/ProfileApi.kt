package com.yazan98.data.api

import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET("users/{username}")
    fun getProfileInfoByUsername(@Path("username") username: String): Single<GithubUser>

    @GET("user")
    fun getProfileInfoByToken(): Single<GithubUser>

    @GET("users/{username}/followers")
    fun getFollowersByToken(@Path("username") username: String): Single<List<GithubUser>>

    @GET("user/followers")
    fun getFollowersByToken(): Single<List<GithubUser>>

    @GET("user/following")
    fun getFollowingByToken(): Single<List<GithubUser>>

    @GET("users/{username}/repos")
    fun getReposByToken(@Path("username") username: String): Single<List<GithubRepositoryModel>>

}
