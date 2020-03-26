package com.yazan98.domain.actions

import com.yazan98.data.models.internal.RepoInfo
import io.vortex.android.VortexAction

interface RepositoryAction: VortexAction {
    class GetRepoTopics: RepositoryAction
    class GetContributorsAction: RepositoryAction
    class GetRepoInfo(private val data: RepoInfo): RepositoryAction {
        fun get() = data
    }

    class GetRepositoryReadme(private val data: RepoInfo): RepositoryAction {
        fun get() = data
    }
}