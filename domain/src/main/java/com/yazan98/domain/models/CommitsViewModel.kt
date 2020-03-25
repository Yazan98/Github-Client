package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.RepositoryComponentImpl
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.data.repos.RepoRepository
import com.yazan98.domain.actions.CommitAction
import com.yazan98.domain.state.CommitState
import com.yazan98.domain.state.HomeState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommitsViewModel @Inject constructor() : VortexViewModel<CommitState, CommitAction>() {

    private val repository: RepoRepository by lazy {
        ReposComponentImpl().getRepositoryComponent()
    }

    override suspend fun execute(newAction: CommitAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is CommitAction.GetCommitsByRepoInfo -> getCommitsByRepoInfo(newAction.get())
            }
        }
    }

    private suspend fun getCommitsByRepoInfo(repoInfo: RepoInfo) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getRepositoryCommits(repoInfo.username, repoInfo.repoName).subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(CommitState.SuccessState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(CommitState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): CommitState {
        return CommitState.EmptyState()
    }

}