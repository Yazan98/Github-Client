package com.yazan98.domain.state

import com.yazan98.data.models.GithubRepositoryModel
import io.vortex.android.state.VortexState

interface StartsState: VortexState {
    class EmptyState: StartsState
    class ErrorState(private val message: String): StartsState {
        fun get() = message
    }

    class StarsResponseState(private val response: List<GithubRepositoryModel>): StartsState {
        fun get() = response
    }
}