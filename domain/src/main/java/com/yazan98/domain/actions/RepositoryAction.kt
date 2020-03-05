package com.yazan98.domain.actions

import com.yazan98.data.models.internal.RepoInfo
import io.vortex.android.VortexAction

interface RepositoryAction: VortexAction {
    class GetRepoInfo(data: RepoInfo): RepositoryAction {
        fun get() = dagger
    }
}