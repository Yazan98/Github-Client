package com.yazan98.data.api

import com.yazan98.data.models.TrendingRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApi {

    @GET("repositories")
    fun getDailyTrending(@Query("since") since: String = "daily"): Single<List<TrendingRepo>>

}