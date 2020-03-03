package com.yazan98.domain.state

import com.yazan98.data.database.models.GithubAccount
import io.realm.OrderedRealmCollection
import io.vortex.android.state.VortexState

interface FollowingState: VortexState {
    class EmptyState: FollowingState
    class FollowingUsersState(private val response: OrderedRealmCollection<GithubAccount>): FollowingState {
        fun get() = response
    }

    class FollowersUsersState(private val response: OrderedRealmCollection<GithubAccount>): FollowingState {
        fun get() = response
    }

    class ErrorState(private val message: String): FollowingState {
        fun get() = message
    }
}
