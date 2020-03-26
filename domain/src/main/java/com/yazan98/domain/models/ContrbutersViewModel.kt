package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.repos.RepoRepository
import com.yazan98.domain.actions.ContributerAction
import com.yazan98.domain.state.CommitState
import com.yazan98.domain.state.ContributerState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContrbutersViewModel @Inject constructor() : VortexViewModel<ContributerState, ContributerAction>() {

    private val repo: RepoRepository by lazy {
        ReposComponentImpl().getRepositoryComponent()
    }

    override suspend fun execute(newAction: ContributerAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is ContributerAction.GetContributersByRepoInfo -> getRepoContributers()
            }
        }
    }

    private suspend fun getRepoContributers() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repo.getRepositoryContributors(
                ApplicationPrefs.getSelectedUsername(),
                ApplicationPrefs.getSelectedRepo()
            ).subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(ContributerState.SuccessState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(ContributerState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): ContributerState {
        return ContributerState.EmptyState()
    }

}