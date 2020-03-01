package com.yazan98.data


import io.vortex.android.prefs.VortexPrefsConfig
import io.vortex.android.prefs.VortexPrefsConsts
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@motif.Scope
interface RepositoryComponent {

    fun getRetrofitConfiguration(): Retrofit

    @motif.Objects
    open class RepositoryObjects {

        fun getRetrofitInstance(retrofitInterceptor: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(retrofitInterceptor)
                .build()
        }

        fun getRetrofitInterceptor(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
//                        .addHeader("Authorization", "token ${ApplicationPrefs.getToken()}")
                        .addHeader("Authorization", Credentials.basic("Yazan98", "Yazan1998005"))
                        .build()
                chain.proceed(request)
            }
            return httpClient.build()
        }

    }
}