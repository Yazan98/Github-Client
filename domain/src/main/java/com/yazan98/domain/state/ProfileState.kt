package com.yazan98.domain.state

import com.yazan98.data.models.ProfileResponse
import io.vortex.android.state.VortexState

interface ProfileState: VortexState {

    class EmptyState: ProfileState
    class SuccessState(private val profileResponse: ProfileResponse): ProfileState {
        fun get() = profileResponse
    }

    class ErrorResponse(private val message: String): ProfileState {
        fun get() = message
    }

}
