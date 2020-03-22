package com.yazan98.domain.state

import com.yazan98.data.models.GithubRepositoryModel
import io.vortex.android.state.VortexState

interface RepositoryState: VortexState {
    class EmptyState: RepositoryState
    class SuccessRepositoryState(private val repo: GithubRepositoryModel): RepositoryState {
        fun get() = repo
    }

    class ErrorState(private val message: String): RepositoryState {
        fun get() = message
    }
}