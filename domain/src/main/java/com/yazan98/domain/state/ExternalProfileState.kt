package com.yazan98.domain.state

import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.models.GithubOrg
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.ProfileResponse
import io.vortex.android.state.VortexState

interface ExternalProfileState: VortexState {

    class LoadingState: ExternalProfileState
    class SuccessState(private val userResponse: ProfileResponse): ExternalProfileState {
        fun get() = userResponse
    }

    class ErrorState(private val error: String): ExternalProfileState {
        fun get() = error
    }

}