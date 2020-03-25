package com.yazan98.domain.actions

import com.yazan98.data.models.internal.RepoInfo
import io.vortex.android.VortexAction

interface CommitAction: VortexAction {
    class GetCommitsByRepoInfo(private val repoInfo: RepoInfo): CommitAction {
        fun get() = repoInfo
    }
}