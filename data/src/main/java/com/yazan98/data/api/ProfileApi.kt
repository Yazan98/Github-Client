package com.yazan98.data.api

import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET("users/{username}")
    fun getProfileInfoByToken(@Path("username") username: String): Flowable<GithubUser>

    @GET("users/{username}/followers")
    fun getFollowersByToken(@Path("username") username: String): Flowable<List<GithubUser>>

    @GET("users/{username}/followers")
    fun getFollowingByToken(@Path("username") username: String): Flowable<List<GithubUser>>

    @GET("users/{username}/repos")
    fun getReposByToken(@Path("username") username: String): Flowable<List<GithubRepositoryModel>>

}
