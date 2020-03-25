package com.yazan98.domain.state

import com.yazan98.data.models.GithubCommit
import io.vortex.android.state.VortexState

interface CommitState : VortexState {
    class EmptyState: CommitState
    class SuccessState(private val data: List<GithubCommit>): CommitState {
        fun get() = data
    }

    class ErrorState(private val error: String): CommitState {
        fun get() = error
    }
}