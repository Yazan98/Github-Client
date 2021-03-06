package com.yazan98.data

import com.yazan98.data.repos.HomeRepository
import com.yazan98.data.repos.ProfileRepository
import com.yazan98.data.repos.RepoRepository
import com.yazan98.data.repos.TrendingRepository

@motif.Scope
interface ReposComponent {

    fun getHomeRepository(): HomeRepository

    fun getRepositoryComponent(): RepoRepository

    fun getTrendingRepository(): TrendingRepository

    fun getProfileRepository(): ProfileRepository

    @motif.Objects
    open class Objects {

        fun getHomeRepo(): HomeRepository {
            return HomeRepository()
        }

        fun getTrendingReositry(): TrendingRepository {
            return TrendingRepository()
        }

        fun getRepo(): RepoRepository {
            return RepoRepository()
        }

        fun getProfile(): ProfileRepository {
            return ProfileRepository()
        }

    }

}
