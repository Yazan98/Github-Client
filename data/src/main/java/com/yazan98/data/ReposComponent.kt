package com.yazan98.data

import com.yazan98.data.repos.HomeRepository

@motif.Scope
interface ReposComponent {

    fun getHomeRepository(): HomeRepository

    @motif.Objects
    open class Objects {

        fun getHomeRepo(): HomeRepository {
            return HomeRepository()
        }

    }

}
