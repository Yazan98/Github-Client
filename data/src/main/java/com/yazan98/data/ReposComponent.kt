package com.yazan98.data

import com.yazan98.data.repos.HomeRepository
import com.yazan98.data.repos.RepoRepository

@motif.Scope
interface ReposComponent {

    fun getHomeRepository(): HomeRepository

    fun getRepositoryComponent(): RepoRepository

    @motif.Objects
    open class Objects {

        fun getHomeRepo(): HomeRepository {
            return HomeRepository()
        }

        fun getRepo(): RepoRepository {
            return RepoRepository()
        }

    }

}
