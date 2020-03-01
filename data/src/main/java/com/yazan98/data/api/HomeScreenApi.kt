package com.yazan98.data.api

import com.yazan98.data.models.GithubNotification
import io.reactivex.Flowable
import retrofit2.http.GET

interface HomeScreenApi {

    @GET("notifications?all=true")
    fun getNotifications(): Flowable<List<GithubNotification>>

}
