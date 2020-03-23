package com.yazan98.domain.state

import com.yazan98.data.models.TrendingRepo
import com.yazan98.data.repos.TrendingRepository
import io.vortex.android.state.VortexState

interface TredingRepositoryState : VortexState {
    class EmptyState: TredingRepositoryState
    class SuccessState(private val result: List<TrendingRepo>): TredingRepositoryState {
        fun get() = result
    }

    class ErrorState(private val message: String): TredingRepositoryState {
        fun get() = message
    }
}