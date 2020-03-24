package com.yazan98.data.repos

import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.BuildConfig
import com.yazan98.data.api.TrendingApi
import com.yazan98.data.models.TrendingRepo
import io.reactivex.Single
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TrendingRepository @Inject constructor() : TrendingApi {

    private val serviceProvider: TrendingApi by lazy {
        getRetrofitInstance(getRetrofitInterceptor()).create(TrendingApi::class.java)
    }

    private fun getRetrofitInstance(retrofitInterceptor: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TRENDING_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(retrofitInterceptor)
            .build()
    }

    private fun getRetrofitInterceptor(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val request =
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", Credentials.basic(ApplicationPrefs.getUsername(), ApplicationPrefs.getPassword()))
                    .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    override fun getDailyTrending(type: String): Single<List<TrendingRepo>> {
        return serviceProvider.getDailyTrending(type)
    }
    
}