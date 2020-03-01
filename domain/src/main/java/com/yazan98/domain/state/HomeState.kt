package com.yazan98.domain.state

import com.yazan98.data.models.GithubNotification
import io.realm.RealmResults
import io.vortex.android.state.VortexState

interface HomeState: VortexState {
    class EmptyState: HomeState
    class NotificationsState(private val response: List<GithubNotification>) : HomeState {
        fun get() = response
    }

    class ErrorState(private val message: String): HomeState {
        fun get() = message
    }
}