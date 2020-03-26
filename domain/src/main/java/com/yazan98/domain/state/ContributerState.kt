package com.yazan98.domain.state

import com.yazan98.data.models.GithubUser
import io.vortex.android.state.VortexState

interface ContributerState : VortexState {
    class EmptyState: ContributerState
    class SuccessState(private val response: List<GithubUser>): ContributerState {
        fun get() = response
    }

    class ErrorState(private val message: String): ContributerState {
        fun get() = message
    }
}
