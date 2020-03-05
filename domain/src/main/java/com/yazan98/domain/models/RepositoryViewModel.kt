package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.data.repos.RepoRepository
import com.yazan98.domain.actions.RepositoryAction
import com.yazan98.domain.state.RepositoryState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(): VortexViewModel<RepositoryState, RepositoryAction>() {

    private val repo: RepoRepository by lazy {
        ReposComponentImpl().getRepositoryComponent()
    }

    override suspend fun execute(newAction: RepositoryAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is RepositoryAction.GetRepoInfo -> getRepoInfo(newAction.get())
            }
        }
    }

    private suspend fun getRepoInfo(repoInfo: RepoInfo) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repo.getRepository(repoInfo.username, repoInfo.repoName).subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(RepositoryState.SuccessRepositoryState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptNewState(RepositoryState.ErrorState(it))
                        acceptLoadingState(false)
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): RepositoryState {
        return RepositoryState.EmptyState()
    }

}
