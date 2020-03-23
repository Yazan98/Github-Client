package com.yazan98.domain.state

import com.yazan98.data.models.FeedResponse
import io.vortex.android.state.VortexState

interface FeedsState : VortexState {
    class EmptyState: FeedsState
    class SuccessState(private val data: List<FeedResponse>): FeedsState {
        fun get() = data
    }

    class ErrorState(private val error: String): FeedsState {
        fun get() = error
    }
}
